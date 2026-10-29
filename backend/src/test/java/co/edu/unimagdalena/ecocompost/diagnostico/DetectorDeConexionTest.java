package co.edu.unimagdalena.ecocompost.diagnostico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import co.edu.unimagdalena.ecocompost.lectura.ConexionProperties;
import co.edu.unimagdalena.ecocompost.lectura.DetectorDeConexion;
import co.edu.unimagdalena.ecocompost.lectura.EstadoConexion;

import org.junit.jupiter.api.Test;

class DetectorDeConexionTest {

	private static final Instant ULTIMA = Instant.parse("2026-10-28T14:00:00Z");

	private final DetectorDeConexion detector = new DetectorDeConexion(new ConexionProperties(5, 3));

	@Test
	void conUnaLecturaReciente() {
		assertThat(detector.evaluar(ULTIMA, ULTIMA.plusSeconds(5))).isEqualTo(EstadoConexion.EN_LINEA);
	}

	@Test
	void tresIntervalosSinDatosAunSonEnLinea() {
		assertThat(detector.evaluar(ULTIMA, ULTIMA.plusSeconds(15))).isEqualTo(EstadoConexion.EN_LINEA);
	}

	@Test
	void masDeTresIntervalosSinDatosEsSinConexion() {
		assertThat(detector.evaluar(ULTIMA, ULTIMA.plusSeconds(16))).isEqualTo(EstadoConexion.SIN_CONEXION);
	}
}
