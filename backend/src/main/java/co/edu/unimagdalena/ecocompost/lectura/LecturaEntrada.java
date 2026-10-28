package co.edu.unimagdalena.ecocompost.lectura;

import java.time.Instant;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Cuerpo de POST /api/lecturas: el mismo contrato para el ESP32 y para el simulador.
 * Los límites son de plausibilidad física del sensor, no de diagnóstico.
 */
public record LecturaEntrada(
		@NotBlank String dispositivoId,
		@NotNull Instant medidoEn,
		@NotNull @DecimalMin("-10") @DecimalMax("90") Double temperaturaC,
		@NotNull @DecimalMin("0") @DecimalMax("100") Double humedadPct,
		OrigenLectura origen) {
}
