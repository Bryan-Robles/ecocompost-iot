package co.edu.unimagdalena.ecocompost.diagnostico;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReglaHumedadTest {

	private final ReglaHumedad regla = new ReglaHumedad(UmbralesDePrueba.propuesta());

	@ParameterizedTest(name = "{0} % -> {1} {2}")
	@CsvSource(nullValues = "-", value = {
			"39.9, CRITICO,  HUM_BAJA",
			"40.0, ATENCION, HUM_BAJA",
			"49.9, ATENCION, HUM_BAJA",
			"50.0, NORMAL,   -",
			"55.0, NORMAL,   -",
			"60.0, NORMAL,   -",
			"60.1, ATENCION, HUM_ALTA",
			"65.0, ATENCION, HUM_ALTA",
			"65.1, CRITICO,  HUM_ALTA",
	})
	void clasificaLosLimites(double humedad, EstadoVariable estado, TipoAlerta tipo) {
		assertThat(regla.evaluar(humedad)).isEqualTo(new ResultadoRegla(estado, tipo));
	}

	@Test
	void respetaUmbralesCambiadosEnConfiguracion() {
		var humedad = new UmbralesProperties.Humedad(52.0, 60.0, 40.0, 65.0, "PENDIENTE");
		var reglaAjustada = new ReglaHumedad(new UmbralesProperties(UmbralesDePrueba.temperatura(), humedad));

		assertThat(reglaAjustada.evaluar(51.0).estado()).isEqualTo(EstadoVariable.ATENCION);
		assertThat(regla.evaluar(51.0).estado()).isEqualTo(EstadoVariable.NORMAL);
	}
}
