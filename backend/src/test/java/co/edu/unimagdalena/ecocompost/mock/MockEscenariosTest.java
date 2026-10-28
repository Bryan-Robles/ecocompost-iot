package co.edu.unimagdalena.ecocompost.mock;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import tools.jackson.databind.json.JsonMapper;

class MockEscenariosTest {

	private final MockEscenarios escenarios = new MockEscenarios(JsonMapper.builder().build(),
			new MockProperties("todo-bien", Path.of("../mock-data").toString(), 4, 7, "mock-01"));

	@Test
	void cargaTodosLosEscenariosDeLaCarpeta() {
		assertThat(escenarios.nombres()).contains("todo-bien", "humedad-baja", "temperatura-alta",
				"sensor-sin-conexion", "ciclo-completo");
		for (String nombre : escenarios.nombres()) {
			assertThat(escenarios.cargar(nombre).curva()).as(nombre).isNotEmpty();
		}
	}

	@Test
	void eventoSinDuracionUsaCero() {
		Escenario.Evento carga = new Escenario.Evento("carga", null, null, null, null);

		assertThat(carga.inicioSeg()).isEqualTo(0);
		assertThat(carga.duracionSeg()).isEqualTo(0);
	}
}
