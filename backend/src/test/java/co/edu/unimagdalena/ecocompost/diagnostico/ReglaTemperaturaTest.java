package co.edu.unimagdalena.ecocompost.diagnostico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class ReglaTemperaturaTest {

	private static final Duration UN_DIA = Duration.ofDays(1);
	private static final Duration VEINTE_DIAS = Duration.ofDays(20);

	private final ReglaTemperatura regla = new ReglaTemperatura(UmbralesDePrueba.propuesta());

	@ParameterizedTest(name = "{0} °C en TERMOFILA -> {1} {2}")
	@CsvSource(nullValues = "-", value = {
			"45.0, NORMAL,   -",
			"58.0, NORMAL,   -",
			"65.0, NORMAL,   -",
			"65.1, ATENCION, TEMP_ALTA",
			"70.0, ATENCION, TEMP_ALTA",
			"70.1, CRITICO,  TEMP_ALTA",
			"44.9, ATENCION, TEMP_BAJA",
			"35.0, ATENCION, TEMP_BAJA",
	})
	void clasificaLosLimitesEnTermofila(double temperatura, EstadoVariable estado, TipoAlerta tipo) {
		assertThat(regla.evaluar(temperatura, Fase.TERMOFILA, UN_DIA)).isEqualTo(new ResultadoRegla(estado, tipo));
	}

	@ParameterizedTest
	@EnumSource(value = Fase.class, names = { "MESOFILA", "ENFRIAMIENTO", "MADURACION", "DESCONOCIDA" })
	void temperaturaBajaEsNormalFueraDeTermofila(Fase fase) {
		assertThat(regla.evaluar(38.0, fase, UN_DIA)).isEqualTo(ResultadoRegla.NORMAL);
	}

	@Test
	void temperaturaAltaEsCriticaEnCualquierFase() {
		assertThat(regla.evaluar(72.0, Fase.MADURACION, VEINTE_DIAS).tipo()).isEqualTo(TipoAlerta.TEMP_ALTA);
	}

	@Test
	void enfriamientoPrematuroEnTermofilaEsCritico() {
		assertThat(regla.evaluar(33.0, Fase.TERMOFILA, UN_DIA))
				.isEqualTo(new ResultadoRegla(EstadoVariable.CRITICO, TipoAlerta.TEMP_BAJA));
	}

	@Test
	void enfriamientoDespuesDelPlazoSoloPideAtencion() {
		assertThat(regla.evaluar(33.0, Fase.TERMOFILA, VEINTE_DIAS))
				.isEqualTo(new ResultadoRegla(EstadoVariable.ATENCION, TipoAlerta.TEMP_BAJA));
	}

	@Test
	void sinDatoDeUltimaCargaNoSeAsumePrematuro() {
		assertThat(regla.evaluar(33.0, Fase.TERMOFILA, null).estado()).isEqualTo(EstadoVariable.ATENCION);
	}

	@Test
	void zonaEntreNormalMaxYCriticaAltaPideAtencion() {
		var temperatura = new UmbralesProperties.Temperatura(45.0, 62.0, 65.0, 40.0, Duration.ofDays(7), "PENDIENTE");
		var reglaAjustada = new ReglaTemperatura(new UmbralesProperties(temperatura, UmbralesDePrueba.humedad()));

		assertThat(reglaAjustada.evaluar(63.0, Fase.TERMOFILA, UN_DIA))
				.isEqualTo(new ResultadoRegla(EstadoVariable.ATENCION, TipoAlerta.TEMP_ALTA));
	}
}
