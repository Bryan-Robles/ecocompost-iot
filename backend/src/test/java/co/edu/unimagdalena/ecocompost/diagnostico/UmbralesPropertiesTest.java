package co.edu.unimagdalena.ecocompost.diagnostico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;

class UmbralesPropertiesTest {

	private final Validator validador = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	void laPropuestaDelEquipoEsCoherente() {
		assertThat(validador.validate(UmbralesDePrueba.propuesta())).isEmpty();
	}

	@Test
	void rechazaHumedadConMinimoMayorQueMaximo() {
		var humedad = new UmbralesProperties.Humedad(60.0, 50.0, 40.0, 65.0, null);
		var propiedades = new UmbralesProperties(UmbralesDePrueba.temperatura(), humedad);

		assertThat(validador.validate(propiedades)).extracting(v -> v.getPropertyPath().toString())
				.containsExactly("humedad.coherente");
	}

	@Test
	void rechazaTemperaturaConCriticaAltaMenorQueNormalMax() {
		var temperatura = new UmbralesProperties.Temperatura(45.0, 65.0, 60.0, 40.0, Duration.ofDays(7), null);
		var propiedades = new UmbralesProperties(temperatura, UmbralesDePrueba.humedad());

		assertThat(validador.validate(propiedades)).extracting(v -> v.getPropertyPath().toString())
				.containsExactly("temperatura.coherente");
	}

	@Test
	void rechazaValoresFaltantes() {
		var humedad = new UmbralesProperties.Humedad(null, 60.0, 40.0, 65.0, null);
		var propiedades = new UmbralesProperties(UmbralesDePrueba.temperatura(), humedad);

		assertThat(validador.validate(propiedades)).extracting(v -> v.getPropertyPath().toString())
				.containsExactly("humedad.normalMin");
	}
}
