package co.edu.unimagdalena.ecocompost.diagnostico;

import java.time.Duration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Umbrales de diagnóstico leídos de {@code ecocompost.diagnostico.*} en application.yml.
 * Si la configuración es incoherente (por ejemplo, normal-min mayor que normal-max), la aplicación no arranca.
 * El respaldo de cada valor está en docs/referencias.md.
 */
@Validated
@ConfigurationProperties(prefix = "ecocompost.diagnostico")
public record UmbralesProperties(@NotNull @Valid Temperatura temperatura, @NotNull @Valid Humedad humedad) {

	/** Umbrales de temperatura en °C. */
	public record Temperatura(
			@NotNull Double normalMin,
			@NotNull Double normalMax,
			@NotNull Double criticaAlta,
			@NotNull Double criticaBajaFaseActiva,
			// Enfriamiento antes de este tiempo desde la última carga se considera prematuro (criterio del equipo)
			@NotNull Duration enfriamientoPrematuroAntesDe,
			String fuente) {

		@AssertTrue(message = "se requiere critica-baja-fase-activa <= normal-min < normal-max <= critica-alta")
		public boolean isCoherente() {
			if (normalMin == null || normalMax == null || criticaAlta == null || criticaBajaFaseActiva == null) {
				return true; // @NotNull ya reporta el valor faltante
			}
			return criticaBajaFaseActiva <= normalMin && normalMin < normalMax && normalMax <= criticaAlta;
		}
	}

	/** Umbrales de humedad en % (0–100). */
	public record Humedad(
			@NotNull Double normalMin,
			@NotNull Double normalMax,
			@NotNull Double criticaBaja,
			@NotNull Double criticaAlta,
			String fuente) {

		@AssertTrue(message = "se requiere 0 <= critica-baja <= normal-min < normal-max <= critica-alta <= 100")
		public boolean isCoherente() {
			if (normalMin == null || normalMax == null || criticaBaja == null || criticaAlta == null) {
				return true; // @NotNull ya reporta el valor faltante
			}
			return 0 <= criticaBaja && criticaBaja <= normalMin && normalMin < normalMax && normalMax <= criticaAlta
					&& criticaAlta <= 100;
		}
	}
}
