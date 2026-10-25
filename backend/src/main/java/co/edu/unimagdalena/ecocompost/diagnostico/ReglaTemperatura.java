package co.edu.unimagdalena.ecocompost.diagnostico;

import java.time.Duration;

import org.springframework.stereotype.Component;

/**
 * Clasifica la temperatura según la fase del compostaje.
 * Una temperatura baja solo es problema en fase TERMOFILA: en MESOFILA, ENFRIAMIENTO o MADURACION es lo esperado.
 * Para distinguir el enfriamiento natural del prematuro se usa el tiempo desde la última carga
 * (criterio del equipo a partir de la Tabla 7 de FAO 2013; ver docs/referencias.md).
 */
@Component
public class ReglaTemperatura {

	private final UmbralesProperties.Temperatura umbrales;

	public ReglaTemperatura(UmbralesProperties propiedades) {
		this.umbrales = propiedades.temperatura();
	}

	/**
	 * @param desdeUltimaCarga tiempo desde la última carga de residuos; null si no se conoce
	 */
	public ResultadoRegla evaluar(double temperaturaC, Fase fase, Duration desdeUltimaCarga) {
		if (temperaturaC > umbrales.criticaAlta()) {
			return ResultadoRegla.critico(TipoAlerta.TEMP_ALTA);
		}
		if (temperaturaC > umbrales.normalMax()) {
			return ResultadoRegla.atencion(TipoAlerta.TEMP_ALTA);
		}
		if (temperaturaC >= umbrales.normalMin() || fase != Fase.TERMOFILA) {
			return ResultadoRegla.NORMAL;
		}
		boolean prematuro = desdeUltimaCarga != null
				&& desdeUltimaCarga.compareTo(umbrales.enfriamientoPrematuroAntesDe()) < 0;
		if (temperaturaC < umbrales.criticaBajaFaseActiva() && prematuro) {
			return ResultadoRegla.critico(TipoAlerta.TEMP_BAJA);
		}
		return ResultadoRegla.atencion(TipoAlerta.TEMP_BAJA);
	}
}
