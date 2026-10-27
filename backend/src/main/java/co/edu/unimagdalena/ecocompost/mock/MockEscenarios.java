package co.edu.unimagdalena.ecocompost.mock;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

/** Carga los escenarios de mock-data/. Solo acepta nombres simples para evitar rutas fuera de la carpeta. */
@Component
@Profile("mock")
public class MockEscenarios {

	private static final Pattern NOMBRE_VALIDO = Pattern.compile("[a-z0-9-]+");

	private final ObjectMapper json;
	private final Path directorio;

	public MockEscenarios(ObjectMapper json, MockProperties propiedades) {
		this.json = json;
		this.directorio = Path.of(propiedades.escenariosDir());
	}

	/** Nombres de los escenarios disponibles (archivos que tienen curva; se excluyen los de pesaje). */
	public List<String> nombres() {
		try (Stream<Path> archivos = Files.list(directorio)) {
			return archivos
					.map(p -> p.getFileName().toString())
					.filter(n -> n.endsWith(".json") && !n.startsWith("cargas"))
					.map(n -> n.substring(0, n.length() - ".json".length()))
					.sorted()
					.toList();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public Escenario cargar(String nombre) {
		if (nombre == null || !NOMBRE_VALIDO.matcher(nombre).matches()) {
			throw new IllegalArgumentException("Nombre de escenario no válido: " + nombre);
		}
		Path archivo = directorio.resolve(nombre + ".json");
		if (!Files.isRegularFile(archivo)) {
			throw new IllegalArgumentException("No existe el escenario " + nombre);
		}
		Escenario e = json.readValue(archivo.toFile(), Escenario.class);
		if (e.curva() == null || e.curva().isEmpty()) {
			throw new IllegalArgumentException("El escenario " + nombre + " no tiene curva");
		}
		List<Escenario.Punto> ordenada = e.curva().stream()
				.sorted(Comparator.comparingDouble(Escenario.Punto::dia))
				.toList();
		return new Escenario(e.nombre() != null ? e.nombre() : nombre, e.semilla() != null ? e.semilla() : 42L,
				e.segundosPorDiaSimulado() != null ? e.segundosPorDiaSimulado() : 60.0, ordenada,
				e.ruido() != null ? e.ruido() : new Escenario.Ruido(0, 0),
				e.eventos() != null ? e.eventos() : List.of());
	}
}
