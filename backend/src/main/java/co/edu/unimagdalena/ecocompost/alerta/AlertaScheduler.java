package co.edu.unimagdalena.ecocompost.alerta;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Revisa el diagnóstico cada 5 s y crea alertas nuevas, también cuando el sensor deja de enviar datos. */
@Component
public class AlertaScheduler {

	private final AlertaService servicio;

	public AlertaScheduler(AlertaService servicio) {
		this.servicio = servicio;
	}

	@Scheduled(fixedDelayString = "${ecocompost.alertas.revision-ms:5000}")
	public void revisar() {
		servicio.sincronizar();
	}
}
