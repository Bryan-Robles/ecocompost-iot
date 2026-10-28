package co.edu.unimagdalena.ecocompost.diagnostico;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import co.edu.unimagdalena.ecocompost.lectura.ConexionProperties;
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
	private final UmbralesProperties umbrales;
	private final FaseProperties fase;
	private final ConexionProperties conexion;
	private final Clock reloj;

	public DiagnosticoService(LecturaService lecturas, ReglaTemperatura reglaTemperatura, ReglaHumedad reglaHumedad,
			UmbralesProperties umbrales, FaseProperties fase, ConexionProperties conexion, Clock reloj) {
		this.lecturas = lecturas;
		this.reglaTemperatura = reglaTemperatura;
		this.reglaHumedad = reglaHumedad;
		this.umbrales = umbrales;
		this.fase = fase;
		this.conexion = conexion;
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
		long segundos = Duration.between(l.getRecibidoEn(), reloj.instant()).toSeconds();
		boolean sinConexion = segundos > 3 * conexion.intervaloEsperadoSegundos();

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
	 * Fase según la temperatura actual y el último pico de la ventana:
	 * termófila si está sobre 45 °C; mesófila si nunca llegó; enfriamiento tras el pico;
	 * maduración cuando el enfriamiento dura al menos diasEnfriamientoAMaduracion días.
	 */
	Fase detectarFase(Lectura actual) {
		double umbralTermofila = umbrales.temperatura().normalMin();
		if (actual.getTemperaturaC() >= umbralTermofila) {
			return Fase.TERMOFILA;
		}
		Instant desde = actual.getMedidoEn().minus(fase.diasVentana(), ChronoUnit.DAYS);
		Lectura pico = null;
		for (Lectura l : lecturas.historial(desde, actual.getMedidoEn())) {
			if (l.getTemperaturaC() >= umbralTermofila) {
				pico = l;
			}
		}
		if (pico == null) {
			return Fase.MESOFILA;
		}
		long dias = ChronoUnit.DAYS.between(pico.getMedidoEn(), actual.getMedidoEn());
		return dias >= fase.diasEnfriamientoAMaduracion() ? Fase.MADURACION : Fase.ENFRIAMIENTO;
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
