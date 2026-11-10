package co.edu.unimagdalena.ecocompost.diagnostico;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.unimagdalena.ecocompost.lectura.DetectorDeConexion;
import co.edu.unimagdalena.ecocompost.lectura.EstadoConexion;
import co.edu.unimagdalena.ecocompost.lectura.Lectura;
import co.edu.unimagdalena.ecocompost.lectura.LecturaService;

import org.springframework.stereotype.Service;

/** Une las reglas de temperatura y humedad, la fase y la conexión en un diagnóstico. */
@Service
public class DiagnosticoService {

	/** Un problema detectado ahora. Lo usa la creación de alertas. */
	public record Hallazgo(TipoAlerta tipo, EstadoVariable estado) {
	}

	public record Medicion(Double valor, EstadoVariable estado) {
	}

	public record Diagnostico(Medicion temperatura, Medicion humedad, Fase fase, String estadoGeneral,
			List<String> acciones, boolean umbralesConFuentePendiente, String conexion, long segundosDesdeUltima) {
	}

	/** Diagnóstico para la API y hallazgos para la creación de alertas. */
	public record Evaluacion(Diagnostico diagnostico, List<Hallazgo> hallazgos) {
	}

	private final LecturaService lecturas;
	private final ReglaTemperatura reglaTemperatura;
	private final ReglaHumedad reglaHumedad;
	private final DetectorDeFase detectorDeFase;
	private final DetectorDeConexion detectorDeConexion;
	private final Clock reloj;

	/** Estado de la fase entre llamadas: solo se alimenta con las lecturas nuevas. */
	private DetectorDeFase.Seguimiento seguimiento;
	private Instant ultimaProcesada = Instant.EPOCH;

	public DiagnosticoService(LecturaService lecturas, ReglaTemperatura reglaTemperatura, ReglaHumedad reglaHumedad,
			DetectorDeFase detectorDeFase, DetectorDeConexion detectorDeConexion, Clock reloj) {
		this.lecturas = lecturas;
		this.reglaTemperatura = reglaTemperatura;
		this.reglaHumedad = reglaHumedad;
		this.detectorDeFase = detectorDeFase;
		this.detectorDeConexion = detectorDeConexion;
		this.reloj = reloj;
	}

	public Evaluacion evaluar() {
		Optional<Lectura> actual = lecturas.actual();
		if (actual.isEmpty()) {
			Diagnostico vacio = new Diagnostico(new Medicion(null, null), new Medicion(null, null), Fase.DESCONOCIDA,
					"SIN_DATOS", List.of(), false, "SIN_CONEXION", 0);
			return new Evaluacion(vacio, List.of());
		}
		Lectura l = actual.get();
		Instant ahora = reloj.instant();
		long segundos = Duration.between(l.getRecibidoEn(), ahora).toSeconds();
		boolean sinConexion = detectorDeConexion.evaluar(l.getRecibidoEn(), ahora) == EstadoConexion.SIN_CONEXION;

		Fase faseActual = detectarFase(l);
		var rt = reglaTemperatura.evaluar(l.getTemperaturaC(), faseActual, null);
		var rh = reglaHumedad.evaluar(l.getHumedadPct());

		List<Hallazgo> hallazgos = new ArrayList<>();
		if (sinConexion) {
			hallazgos.add(new Hallazgo(TipoAlerta.SIN_CONEXION, EstadoVariable.ATENCION));
		}
		if (rt.tipo() != null) {
			hallazgos.add(new Hallazgo(rt.tipo(), rt.estado()));
		}
		if (rh.tipo() != null) {
			hallazgos.add(new Hallazgo(rh.tipo(), rh.estado()));
		}

		List<String> acciones = hallazgos.stream()
				.flatMap(h -> TextosAlerta.de(h.tipo()).queHacer().stream())
				.toList();
		String estadoGeneral = sinConexion ? "SIN_DATOS" : peorEstado(rt.estado(), rh.estado());

		Diagnostico d = new Diagnostico(
				new Medicion(l.getTemperaturaC(), rt.estado()),
				new Medicion(l.getHumedadPct(), rh.estado()),
				faseActual,
				estadoGeneral,
				acciones,
				false,
				sinConexion ? "SIN_CONEXION" : "EN_LINEA",
				segundos);
		return new Evaluacion(d, hallazgos);
	}

	/**
	 * Fase de la lectura actual. Avanza el seguimiento solo con las lecturas posteriores a la última procesada.
	 * Si la lectura actual es anterior a la última procesada (p. ej. tras borrar los datos simulados),
	 * el seguimiento se reconstruye desde el inicio.
	 */
	synchronized Fase detectarFase(Lectura actual) {
		if (seguimiento == null || actual.getMedidoEn().isBefore(ultimaProcesada)) {
			seguimiento = detectorDeFase.nuevoSeguimiento();
			ultimaProcesada = Instant.EPOCH;
		}
		if (actual.getMedidoEn().isAfter(ultimaProcesada)) {
			for (Lectura l : lecturas.historial(ultimaProcesada, actual.getMedidoEn())) {
				if (l.getMedidoEn().isAfter(ultimaProcesada)) {
					seguimiento.avanzar(l);
					ultimaProcesada = l.getMedidoEn();
				}
			}
		}
		return seguimiento.fase();
	}

	private static String peorEstado(EstadoVariable a, EstadoVariable b) {
		if (a == EstadoVariable.CRITICO || b == EstadoVariable.CRITICO) {
			return "CRITICO";
		}
		if (a == EstadoVariable.ATENCION || b == EstadoVariable.ATENCION) {
			return "ATENCION";
		}
		return "TODO_BIEN";
	}
}
