package co.edu.unimagdalena.ecocompost.diagnostico;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Parámetros de la detección de fase (análisis, sección 4.2). Son criterios del equipo, no de la FAO:
 * la FAO (2013, Tabla 7) da 2 a 5 semanas para la fase termófila y no fija un plazo de maduración.
 * El respaldo está en docs/referencias.md.
 *
 * @param diasVentana historial que se recorre para detectar la fase, en días
 * @param ventanaLecturas tamaño de la media móvil y de la histéresis, en lecturas
 * @param diasMinimosMaduracion días con temperatura ≤ ambiente + margen antes de pasar a maduración
 * @param margenSobreAmbiente margen sobre la temperatura ambiente, en °C
 * @param temperaturaAmbiente temperatura ambiente, en °C; sin fuente (medir o citar al IDEAM)
 */
@ConfigurationProperties(prefix = "ecocompost.diagnostico.fase")
public record FaseProperties(
		@DefaultValue("14") int diasVentana,
		@DefaultValue("12") int ventanaLecturas,
		@DefaultValue("3") int diasMinimosMaduracion,
		@DefaultValue("5.0") double margenSobreAmbiente,
		@DefaultValue("30.0") double temperaturaAmbiente) {
}
