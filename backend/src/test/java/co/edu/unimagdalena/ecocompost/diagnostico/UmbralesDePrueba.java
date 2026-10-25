package co.edu.unimagdalena.ecocompost.diagnostico;

import java.time.Duration;

/** Umbrales iguales a la propuesta de application.yml, para pruebas sin Spring. */
final class UmbralesDePrueba {

	private UmbralesDePrueba() {
	}

	static UmbralesProperties.Temperatura temperatura() {
		return new UmbralesProperties.Temperatura(45.0, 65.0, 70.0, 35.0, Duration.ofDays(14), "FAO-2013");
	}

	static UmbralesProperties.Humedad humedad() {
		return new UmbralesProperties.Humedad(45.0, 60.0, 40.0, 65.0, "FAO-2013");
	}

	static UmbralesProperties propuesta() {
		return new UmbralesProperties(temperatura(), humedad());
	}
}
