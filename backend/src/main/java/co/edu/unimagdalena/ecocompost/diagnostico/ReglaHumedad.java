package co.edu.unimagdalena.ecocompost.diagnostico;

import org.springframework.stereotype.Component;

/**
 * Clasifica la humedad. El rango normal es inclusivo (50,0 % es NORMAL).
 * Entre el rango normal y el umbral crítico, el estado es ATENCION.
 */
@Component
public class ReglaHumedad {

	private final UmbralesProperties.Humedad umbrales;

	public ReglaHumedad(UmbralesProperties propiedades) {
		this.umbrales = propiedades.humedad();
	}

	public ResultadoRegla evaluar(double humedadPct) {
		if (humedadPct < umbrales.criticaBaja()) {
			return ResultadoRegla.critico(TipoAlerta.HUM_BAJA);
		}
		if (humedadPct < umbrales.normalMin()) {
			return ResultadoRegla.atencion(TipoAlerta.HUM_BAJA);
		}
		if (humedadPct <= umbrales.normalMax()) {
			return ResultadoRegla.NORMAL;
		}
		if (humedadPct <= umbrales.criticaAlta()) {
			return ResultadoRegla.atencion(TipoAlerta.HUM_ALTA);
		}
		return ResultadoRegla.critico(TipoAlerta.HUM_ALTA);
	}
}
