package co.edu.unimagdalena.ecocompost.alerta;

import java.time.Instant;
import java.util.List;

import co.edu.unimagdalena.ecocompost.diagnostico.TipoAlerta;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alertas")
public class AlertasController {

	/** Forma de una alerta; los nombres coinciden con src/api/tipos.ts. */
	public record AlertaRespuesta(Long id, TipoAlerta tipo, Severidad severidad, String titulo, String queSignifica,
			List<String> queHacer, Instant creadaEn, Instant atendidaEn) {

		static AlertaRespuesta de(Alerta a) {
			return new AlertaRespuesta(a.getId(), a.getTipo(), a.getSeveridad(), a.getTitulo(), a.getQueSignifica(),
					List.of(a.getQueHacer().split("\n")), a.getCreadaEn(), a.getAtendidaEn());
		}
	}

	private final AlertaService servicio;

	public AlertasController(AlertaService servicio) {
		this.servicio = servicio;
	}

	/** estado: abiertas, atendidas o todas (por defecto). */
	@GetMapping
	public List<AlertaRespuesta> listar(@RequestParam(defaultValue = "todas") String estado) {
		return servicio.listar().stream()
				.filter(a -> switch (estado) {
					case "abiertas" -> a.getAtendidaEn() == null;
					case "atendidas" -> a.getAtendidaEn() != null;
					default -> true;
				})
				.map(AlertaRespuesta::de)
				.toList();
	}

	@PatchMapping("/{id}/atender")
	public AlertaRespuesta atender(@PathVariable Long id) {
		return AlertaRespuesta.de(servicio.atender(id));
	}
}
