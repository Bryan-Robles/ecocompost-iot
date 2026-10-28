package co.edu.unimagdalena.ecocompost.alerta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlertaNoEncontrada extends RuntimeException {

	public AlertaNoEncontrada(Long id) {
		super("No existe la alerta " + id);
	}
}
