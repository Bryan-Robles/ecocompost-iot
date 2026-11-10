package co.edu.unimagdalena.ecocompost.lectura;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Component;

/** El sensor está sin conexión cuando pasan más de {@code intervalosSinDatos} intervalos sin lecturas. */
@Component
public class DetectorDeConexion {

	private final ConexionProperties conexion;

	public DetectorDeConexion(ConexionProperties conexion) {
		this.conexion = conexion;
	}

	public EstadoConexion evaluar(Instant ultima, Instant ahora) {
		long segundos = Duration.between(ultima, ahora).toSeconds();
		long limite = conexion.intervalosSinDatos() * conexion.intervaloEsperadoSegundos();
		return segundos > limite ? EstadoConexion.SIN_CONEXION : EstadoConexion.EN_LINEA;
	}
}
