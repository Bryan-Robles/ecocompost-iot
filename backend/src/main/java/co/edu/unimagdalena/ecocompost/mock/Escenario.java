package co.edu.unimagdalena.ecocompost.mock;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Escenario de demo leído de mock-data/*.json. Solo se usan los campos que el simulador necesita;
 * los demás (descripción, origen, valoresIlustrativos, eventos de carga) se ignoran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Escenario(
		String nombre,
		Long semilla,
		Double segundosPorDiaSimulado,
		List<Punto> curva,
		Ruido ruido,
		List<Evento> eventos) {

	/** Punto de control de la curva biológica: día simulado y valores medios. */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Punto(double dia, double temperaturaC, double humedadPct) {
	}

	/** Desviación estándar del ruido gaussiano por variable. */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Ruido(double temperaturaSigma, double humedadSigma) {
	}

	/**
	 * Episodio programado. tipo "rampa": la variable llega a {@code hasta} en {@code duracionSeg} y se mantiene.
	 * tipo "corte": el sensor no envía lecturas durante {@code duracionSeg}. Tiempos en segundos reales desde el cambio de escenario.
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Evento(String tipo, String variable, double inicioSeg, double duracionSeg, Double hasta) {
	}
}
