package co.edu.unimagdalena.ecocompost.lectura;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/** Una medición de temperatura y humedad. Un mismo dispositivo no puede repetir la hora de medición. */
@Entity
@Table(name = "lectura", uniqueConstraints = @UniqueConstraint(columnNames = { "dispositivoId", "medidoEn" }))
public class Lectura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String dispositivoId;

	/** Hora de la medición según el sensor (o el simulador). */
	@Column(nullable = false)
	private Instant medidoEn;

	/** Hora en que el servidor recibió la lectura; sirve para detectar sensor sin conexión. */
	@Column(nullable = false)
	private Instant recibidoEn;

	@Column(nullable = false)
	private double temperaturaC;

	@Column(nullable = false)
	private double humedadPct;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrigenLectura origen;

	protected Lectura() {
	}

	public Lectura(String dispositivoId, Instant medidoEn, Instant recibidoEn, double temperaturaC, double humedadPct,
			OrigenLectura origen) {
		this.dispositivoId = dispositivoId;
		this.medidoEn = medidoEn;
		this.recibidoEn = recibidoEn;
		this.temperaturaC = temperaturaC;
		this.humedadPct = humedadPct;
		this.origen = origen;
	}

	public Long getId() {
		return id;
	}

	public String getDispositivoId() {
		return dispositivoId;
	}

	public Instant getMedidoEn() {
		return medidoEn;
	}

	public Instant getRecibidoEn() {
		return recibidoEn;
	}

	public double getTemperaturaC() {
		return temperaturaC;
	}

	public double getHumedadPct() {
		return humedadPct;
	}

	public OrigenLectura getOrigen() {
		return origen;
	}
}
