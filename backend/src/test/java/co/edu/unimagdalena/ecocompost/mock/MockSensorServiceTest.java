package co.edu.unimagdalena.ecocompost.mock;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class MockSensorServiceTest {

	private static final List<Escenario.Punto> CURVA = List.of(
			new Escenario.Punto(0, 30, 58),
			new Escenario.Punto(4, 60, 55));

	@Test
	void mantieneLosExtremosFueraDeLaCurva() {
		assertThat(MockSensorService.interpolar(CURVA, -2)).containsExactly(30, 58);
		assertThat(MockSensorService.interpolar(CURVA, 10)).containsExactly(60, 55);
	}

	@Test
	void pasaPorLosPuntosDeControl() {
		assertThat(MockSensorService.interpolar(CURVA, 0)).containsExactly(30, 58);
		assertThat(MockSensorService.interpolar(CURVA, 4)).containsExactly(60, 55);
	}

	@Test
	void interpolaConCosenoEnElCentro() {
		double[] mitad = MockSensorService.interpolar(CURVA, 2);

		assertThat(mitad[0]).isCloseTo(45.0, org.assertj.core.api.Assertions.within(1e-9));
		assertThat(mitad[1]).isCloseTo(56.5, org.assertj.core.api.Assertions.within(1e-9));
	}
}
