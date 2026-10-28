package co.edu.unimagdalena.ecocompost.mock;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Simulador del sensor (perfil "mock").
 * diaInicial: día de la curva en el que arranca la demo (4 = ya en fase termófila).
 * diasHistorial: días simulados que se precargan antes de arrancar.
 */
@ConfigurationProperties(prefix = "ecocompost.mock")
public record MockProperties(
		@DefaultValue("todo-bien") String escenarioInicial,
		@DefaultValue("../mock-data") String escenariosDir,
		@DefaultValue("4") double diaInicial,
		@DefaultValue("7") int diasHistorial,
		@DefaultValue("mock-01") String dispositivoId) {
}
