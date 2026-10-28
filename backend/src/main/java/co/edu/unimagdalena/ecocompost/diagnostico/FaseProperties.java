package co.edu.unimagdalena.ecocompost.diagnostico;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Parámetros de la detección de fase. Son criterios del equipo (ver docs/referencias.md):
 * la FAO (2013, Tabla 7) da 2 a 5 semanas para la fase termófila y no fija un plazo de maduración.
 */
@ConfigurationProperties(prefix = "ecocompost.diagnostico.fase")
public record FaseProperties(
		@DefaultValue("14") int diasVentana,
		@DefaultValue("14") int diasEnfriamientoAMaduracion) {
}
