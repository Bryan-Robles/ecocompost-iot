package co.edu.unimagdalena.ecocompost.diagnostico;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/diagnostico")
public class DiagnosticoController {

	private final DiagnosticoService servicio;

	public DiagnosticoController(DiagnosticoService servicio) {
		this.servicio = servicio;
	}

	@GetMapping
	public DiagnosticoService.Diagnostico diagnostico() {
		return servicio.evaluar().diagnostico();
	}
}
