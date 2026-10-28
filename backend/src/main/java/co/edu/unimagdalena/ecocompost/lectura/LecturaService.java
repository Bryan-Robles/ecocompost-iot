package co.edu.unimagdalena.ecocompost.lectura;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LecturaService {

	private final LecturaRepository repositorio;
	private final Clock reloj;

	public LecturaService(LecturaRepository repositorio, Clock reloj) {
		this.repositorio = repositorio;
		this.reloj = reloj;
	}

	/** Resultado de registrar una lectura: {@code creada} es false si ya existía (reenvío del búfer, riesgo R4). */
	public record Registro(Lectura lectura, boolean creada) {
	}

	@Transactional
	public Registro registrar(LecturaEntrada entrada) {
		// La base guarda milisegundos: se truncan antes de buscar duplicados para que coincidan.
		Instant medidoEn = entrada.medidoEn().truncatedTo(ChronoUnit.MILLIS);
		Optional<Lectura> existente = repositorio.findFirstByDispositivoIdAndMedidoEn(entrada.dispositivoId(), medidoEn);
		if (existente.isPresent()) {
			return new Registro(existente.get(), false);
		}
		Lectura lectura = new Lectura(entrada.dispositivoId(), medidoEn, reloj.instant(),
				entrada.temperaturaC(), entrada.humedadPct(),
				entrada.origen() == null ? OrigenLectura.ESP32 : entrada.origen());
		return new Registro(repositorio.save(lectura), true);
	}

	/** Borra solo las lecturas del simulador; las del ESP32 y las manuales no se tocan. */
	@Transactional
	public void borrarSimulados() {
		repositorio.borrarPorOrigen(OrigenLectura.SIMULADO);
	}

	public Optional<Lectura> actual() {
		return repositorio.findFirstByOrderByMedidoEnDesc();
	}

	public List<Lectura> historial(Instant desde, Instant hasta) {
		return repositorio.findByMedidoEnBetweenOrderByMedidoEnAsc(desde, hasta);
	}
}
