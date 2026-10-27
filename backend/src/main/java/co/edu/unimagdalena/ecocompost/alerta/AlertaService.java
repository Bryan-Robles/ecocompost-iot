package co.edu.unimagdalena.ecocompost.alerta;

import java.time.Clock;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService;
import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService.Diagnostico;
import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService.Evaluacion;
import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService.Hallazgo;
import co.edu.unimagdalena.ecocompost.diagnostico.EstadoVariable;
import co.edu.unimagdalena.ecocompost.diagnostico.TextosAlerta;
import co.edu.unimagdalena.ecocompost.diagnostico.TipoAlerta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertaService {

	private final AlertaRepository repositorio;
	private final DiagnosticoService diagnosticos;
	private final Clock reloj;
	/** Problemas que estaban activos en la revisión anterior. */
	private final Set<TipoAlerta> activosAntes = new HashSet<>();

	public AlertaService(AlertaRepository repositorio, DiagnosticoService diagnosticos, Clock reloj) {
		this.repositorio = repositorio;
		this.diagnosticos = diagnosticos;
		this.reloj = reloj;
	}

	/**
	 * Una alerta nueva solo aparece cuando un problema empieza (o vuelve después de haber desaparecido).
	 * Mientras el problema siga activo, la alerta abierta se actualiza; si ya fue atendida, no se repite.
	 */
	@Transactional
	public synchronized void sincronizar() {
		Evaluacion evaluacion = diagnosticos.evaluar();
		Set<TipoAlerta> activos = new HashSet<>();
		for (Hallazgo h : evaluacion.hallazgos()) {
			activos.add(h.tipo());
			Severidad severidad = severidad(h);
			Optional<Alerta> abierta = repositorio.findFirstByTipoAndAtendidaEnIsNull(h.tipo());
			if (abierta.isPresent()) {
				abierta.get().actualizar(severidad, titulo(h.tipo(), evaluacion.diagnostico()));
			} else if (!activosAntes.contains(h.tipo())) {
				repositorio.save(crear(h, evaluacion.diagnostico()));
			}
		}
		activosAntes.clear();
		activosAntes.addAll(activos);
	}

	@Transactional(readOnly = true)
	public List<Alerta> listar() {
		return repositorio.findAllByOrderByCreadaEnDesc();
	}

	@Transactional
	public Alerta atender(Long id) {
		Alerta alerta = repositorio.findById(id)
				.orElseThrow(() -> new AlertaNoEncontrada(id));
		alerta.atender(reloj.instant());
		return alerta;
	}

	private Alerta crear(Hallazgo h, Diagnostico d) {
		TextosAlerta.Texto texto = TextosAlerta.de(h.tipo());
		return new Alerta(h.tipo(), severidad(h), titulo(h.tipo(), d), texto.queSignifica(),
				String.join("\n", texto.queHacer()), Instant.now(reloj));
	}

	private static Severidad severidad(Hallazgo h) {
		return h.estado() == EstadoVariable.CRITICO ? Severidad.CRITICA : Severidad.ATENCION;
	}

	private static String titulo(TipoAlerta tipo, Diagnostico d) {
		return switch (tipo) {
			case TEMP_ALTA -> "Temperatura alta (" + redondear(d.temperatura().valor()) + " °C)";
			case TEMP_BAJA -> "Temperatura baja (" + redondear(d.temperatura().valor()) + " °C)";
			case HUM_BAJA -> "Humedad baja (" + redondear(d.humedad().valor()) + " %)";
			case HUM_ALTA -> "Humedad alta (" + redondear(d.humedad().valor()) + " %)";
			case SIN_CONEXION -> "Sensor sin conexión";
		};
	}

	private static String redondear(Double valor) {
		return valor == null ? "—" : String.valueOf(Math.round(valor));
	}
}
