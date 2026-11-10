package co.edu.unimagdalena.ecocompost.diagnostico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;

import co.edu.unimagdalena.ecocompost.lectura.Lectura;
import co.edu.unimagdalena.ecocompost.lectura.OrigenLectura;

import org.junit.jupiter.api.Test;

class DetectorDeFaseTest {

	private static final Instant INICIO = Instant.parse("2026-10-01T00:00:00Z");
	private static final FaseProperties PROPIEDADES = new FaseProperties(12, 3, 5.0, 30.0);

	@Test
	void sinLecturasLaFaseEsDesconocida() {
		assertThat(new DetectorDeFase(UmbralesDePrueba.propuesta(), PROPIEDADES).detectar(List.of()))
				.isEqualTo(Fase.DESCONOCIDA);
	}

	@Test
	void sinPicoDeTemperaturaLaFaseEsMesofila() {
		assertThat(fase(repetir(25, 30))).isEqualTo(Fase.MESOFILA);
	}

	@Test
	void unaVentanaSostenidaSobreElUmbralEntraEnTermofila() {
		assertThat(fase(repetir(50, 12))).isEqualTo(Fase.TERMOFILA);
	}

	@Test
	void unPicoAisladoNoCambiaLaFase() {
		assertThat(fase(concat(repetir(25, 30), new double[] { 60 }, repetir(25, 10)))).isEqualTo(Fase.MESOFILA);
	}

	@Test
	void unaBajadaCortaNoSaleDeTermofila() {
		assertThat(fase(concat(repetir(50, 20), new double[] { 30 }))).isEqualTo(Fase.TERMOFILA);
	}

	@Test
	void unEnfriamientoSostenidoPasaAEnfriamiento() {
		assertThat(fase(concat(repetir(50, 20), repetir(40, 30)))).isEqualTo(Fase.ENFRIAMIENTO);
	}

	@Test
	void volverASubirLaTemperaturaRegresaATermofila() {
		assertThat(fase(concat(repetir(50, 20), repetir(40, 30), repetir(50, 20)))).isEqualTo(Fase.TERMOFILA);
	}

	@Test
	void temperaturaBajaPorMenosDeTresDiasNoLlegaAMaduracion() {
		assertThat(fase(concat(repetir(50, 20), repetir(40, 30), repetir(30, 40)))).isEqualTo(Fase.ENFRIAMIENTO);
	}

	@Test
	void temperaturaBajaPorTresDiasLlegaAMaduracion() {
		assertThat(fase(concat(repetir(50, 20), repetir(40, 12), repetir(30, 96)))).isEqualTo(Fase.MADURACION);
	}

	/** Una lectura por hora, con temperaturas en orden. */
	private static Fase fase(double[] temperaturas) {
		return new DetectorDeFase(UmbralesDePrueba.propuesta(), PROPIEDADES).detectar(historial(temperaturas));
	}

	private static List<Lectura> historial(double[] temperaturas) {
		List<Lectura> lecturas = new ArrayList<>();
		for (int i = 0; i < temperaturas.length; i++) {
			Instant medidoEn = INICIO.plus(Duration.ofHours(i));
			lecturas.add(new Lectura("mock-01", medidoEn, medidoEn, temperaturas[i], 50.0, OrigenLectura.SIMULADO));
		}
		return lecturas;
	}

	private static double[] repetir(double temperatura, int veces) {
		return DoubleStream.generate(() -> temperatura).limit(veces).toArray();
	}

	private static double[] concat(double[]... partes) {
		return Arrays.stream(partes).flatMapToDouble(DoubleStream::of).toArray();
	}
}
