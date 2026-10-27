package co.edu.unimagdalena.ecocompost;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;

/** Flujo completo por HTTP: ingesta idempotente, validación, diagnóstico y alertas. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PrototipoIntegracionTest {

	@Value("${local.server.port}")
	private int puerto;

	private RestClient cliente() {
		return RestClient.create("http://localhost:" + puerto);
	}

	@Test
	void registraUnaLecturaUnaSolaVez() {
		Instant medido = Instant.now().minusSeconds(60);
		Map<String, Object> cuerpo = Map.of("dispositivoId", "test-int-1", "medidoEn", medido.toString(),
				"temperaturaC", 50.0, "humedadPct", 55.0, "origen", "ESP32");

		var primera = cliente().post().uri("/api/lecturas").contentType(MediaType.APPLICATION_JSON).body(cuerpo)
				.retrieve().toEntity(JsonNode.class);
		var reenvio = cliente().post().uri("/api/lecturas").contentType(MediaType.APPLICATION_JSON).body(cuerpo)
				.retrieve().toEntity(JsonNode.class);

		assertThat(primera.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(reenvio.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(reenvio.getBody().get("id").asLong()).isEqualTo(primera.getBody().get("id").asLong());
	}

	@Test
	void rechazaHumedadFueraDeRangoFisico() {
		Map<String, Object> cuerpo = Map.of("dispositivoId", "test-int-2", "medidoEn", Instant.now().toString(),
				"temperaturaC", 40.0, "humedadPct", 120.0);

		assertThatBadRequest(() -> cliente().post().uri("/api/lecturas").contentType(MediaType.APPLICATION_JSON)
				.body(cuerpo).retrieve().toBodilessEntity());
	}

	@Test
	void diagnosticoEnTermofilaConTemperaturaYHumedadNormales() {
		Map<String, Object> cuerpo = Map.of("dispositivoId", "test-int-3", "medidoEn", Instant.now().toString(),
				"temperaturaC", 52.0, "humedadPct", 55.0);
		cliente().post().uri("/api/lecturas").contentType(MediaType.APPLICATION_JSON).body(cuerpo).retrieve()
				.toBodilessEntity();

		JsonNode d = cliente().get().uri("/api/diagnostico").retrieve().body(JsonNode.class);

		assertThat(d.get("fase").asString()).isEqualTo("TERMOFILA");
		assertThat(d.get("estadoGeneral").asString()).isEqualTo("TODO_BIEN");
		assertThat(d.get("conexion").asString()).isEqualTo("EN_LINEA");
	}

	@Test
	void atenderUnaAlertaInexistenteDevuelveNoEncontrada() {
		assertThatBadRequest(() -> cliente().patch().uri("/api/alertas/999999999/atender").retrieve()
				.toBodilessEntity(), HttpStatus.NOT_FOUND);
	}

	private static void assertThatBadRequest(Runnable accion) {
		assertThatBadRequest(accion, HttpStatus.BAD_REQUEST);
	}

	private static void assertThatBadRequest(Runnable accion, HttpStatus esperado) {
		try {
			accion.run();
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode()).isEqualTo(esperado);
			return;
		}
		throw new AssertionError("Se esperaba " + esperado);
	}
}
