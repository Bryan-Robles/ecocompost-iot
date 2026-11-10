package co.edu.unimagdalena.ecocompost.lectura;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lecturas")
public class LecturaController {

	private final LecturaService servicio;
	private final DetectorDeConexion detectorDeConexion;
	private final Clock reloj;

	public LecturaController(LecturaService servicio, DetectorDeConexion detectorDeConexion, Clock reloj) {
		this.servicio = servicio;
		this.detectorDeConexion = detectorDeConexion;
		this.reloj = reloj;
	}

	/** Respuesta de una lectura. Los nombres coinciden con src/api/tipos.ts del frontend. */
	public record LecturaRespuesta(Long id, String dispositivoId, Instant medidoEn, Instant recibidoEn,
			double temperaturaC, double humedadPct, OrigenLectura origen) {

		static LecturaRespuesta de(Lectura l) {
			return new LecturaRespuesta(l.getId(), l.getDispositivoId(), l.getMedidoEn(), l.getRecibidoEn(),
					l.getTemperaturaC(), l.getHumedadPct(), l.getOrigen());
		}
	}

	public record LecturaActualRespuesta(LecturaRespuesta lectura, Instant recibidoEn, String conexion,
			long segundosDesdeUltima) {
	}

	/** 201 si la lectura es nueva; 200 si ya existía (reenvío). */
	@PostMapping
	public ResponseEntity<LecturaRespuesta> registrar(@Valid @RequestBody LecturaEntrada entrada) {
		LecturaService.Registro registro = servicio.registrar(entrada);
		HttpStatus estado = registro.creada() ? HttpStatus.CREATED : HttpStatus.OK;
		return ResponseEntity.status(estado).body(LecturaRespuesta.de(registro.lectura()));
	}

	/** Última lectura y estado de conexión: SIN_CONEXION si no llegan datos en 3 intervalos esperados (R4). */
	@GetMapping("/actual")
	public ResponseEntity<LecturaActualRespuesta> actual() {
		return servicio.actual()
				.map(l -> {
					Instant ahora = reloj.instant();
					long segundos = Duration.between(l.getRecibidoEn(), ahora).toSeconds();
					EstadoConexion estado = detectorDeConexion.evaluar(l.getRecibidoEn(), ahora);
					return ResponseEntity.ok(new LecturaActualRespuesta(LecturaRespuesta.de(l), l.getRecibidoEn(),
							estado.name(), segundos));
				})
				.orElseGet(() -> ResponseEntity.noContent().build());
	}

	@GetMapping
	public List<LecturaRespuesta> historial(@RequestParam Instant desde, @RequestParam Instant hasta) {
		return servicio.historial(desde, hasta).stream().map(LecturaRespuesta::de).toList();
	}
}
