package co.edu.unimagdalena.ecocompost.alerta;

import java.time.Instant;

import co.edu.unimagdalena.ecocompost.diagnostico.TipoAlerta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "alerta")
public class Alerta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAlerta tipo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Severidad severidad;

	@Column(nullable = false)
	private String titulo;

	@Column(nullable = false)
	private String queSignifica;

	/** Pasos separados por salto de línea. */
	@Lob
	@Column(nullable = false)
	private String queHacer;

	@Column(nullable = false)
	private Instant creadaEn;

	private Instant atendidaEn;

	protected Alerta() {
	}

	public Alerta(TipoAlerta tipo, Severidad severidad, String titulo, String queSignifica, String queHacer,
			Instant creadaEn) {
		this.tipo = tipo;
		this.severidad = severidad;
		this.titulo = titulo;
		this.queSignifica = queSignifica;
		this.queHacer = queHacer;
		this.creadaEn = creadaEn;
	}

	/** Actualiza severidad y título de una alerta abierta cuando cambia el valor medido. */
	public void actualizar(Severidad nuevaSeveridad, String nuevoTitulo) {
		this.severidad = nuevaSeveridad;
		this.titulo = nuevoTitulo;
	}

	public void atender(Instant cuando) {
		if (atendidaEn == null) {
			atendidaEn = cuando;
		}
	}

	public Long getId() {
		return id;
	}

	public TipoAlerta getTipo() {
		return tipo;
	}

	public Severidad getSeveridad() {
		return severidad;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getQueSignifica() {
		return queSignifica;
	}

	public String getQueHacer() {
		return queHacer;
	}

	public Instant getCreadaEn() {
		return creadaEn;
	}

	public Instant getAtendidaEn() {
		return atendidaEn;
	}
}
