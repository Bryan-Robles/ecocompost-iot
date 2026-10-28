package co.edu.unimagdalena.ecocompost.mock;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controles de la demo. Solo existe con el perfil "mock". */
@RestController
@RequestMapping("/api/demo")
@Profile("mock")
public class DemoController {

	public record CambioEscenario(@NotBlank String nombre) {
	}

	private final MockSensorService simulador;

	public DemoController(MockSensorService simulador) {
		this.simulador = simulador;
	}

	@GetMapping("/escenarios")
	public Map<String, Object> escenarios() {
		return Map.of("actual", String.valueOf(simulador.escenarioActual()), "disponibles", simulador.escenariosDisponibles());
	}

	@PostMapping("/escenario")
	public ResponseEntity<Map<String, String>> cambiar(@Valid @RequestBody CambioEscenario cuerpo) {
		simulador.cambiar(cuerpo.nombre());
		return ResponseEntity.ok(Map.of("actual", cuerpo.nombre()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<Map<String, String>> escenarioNoValido(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
	}
}
