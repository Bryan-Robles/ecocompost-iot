package co.edu.unimagdalena.ecocompost.lectura;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Intervalo esperado entre lecturas, en segundos. El sensor está "sin conexión" tras 3 intervalos sin datos.
 * El simulador envía cada 5 s; un ESP32 real debería configurar varios minutos.
 */
@ConfigurationProperties(prefix = "ecocompost.conexion")
public record ConexionProperties(@DefaultValue("5") long intervaloEsperadoSegundos) {
}
