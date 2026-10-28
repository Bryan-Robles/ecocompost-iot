package co.edu.unimagdalena.ecocompost.mock;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

import co.edu.unimagdalena.ecocompost.alerta.AlertaRepository;
import co.edu.unimagdalena.ecocompost.lectura.LecturaEntrada;
import co.edu.unimagdalena.ecocompost.lectura.LecturaService;
import co.edu.unimagdalena.ecocompost.lectura.OrigenLectura;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reemplaza al ESP32 en el prototipo. Envía lecturas por LecturaService, el mismo camino que usa POST /api/lecturas.
 *
 * El tiempo de la simulación corre a la velocidad del escenario (segundosPorDiaSimulado). Cada lectura lleva la hora
 * simulada en medidoEn; recibidoEn es la hora real. Por eso la detección de fase usa días simulados y la conexión usa
 * tiempo real.
 */
@Service
@Profile("mock")
public class MockSensorService {

	private static final double SEGUNDOS_POR_DIA = 86_400.0;
	private static final int PASOS_HISTORIAL_POR_DIA = 24;

	private final MockProperties propiedades;
	private final MockEscenarios escenarios;
	private final LecturaService lecturas;
	private final AlertaRepository alertas;
	private final Clock reloj;

	private final Instant origenSimulado;
	private Escenario escenario;
	private Random ruido;
	/** Día de la curva en el instante del último cambio de escenario, y ese instante real. */
	private double diaAlCambio;
	private Instant instanteCambio;
	private boolean activo;

	public MockSensorService(MockProperties propiedades, MockEscenarios escenarios, LecturaService lecturas,
			AlertaRepository alertas, Clock reloj) {
		this.propiedades = propiedades;
		this.escenarios = escenarios;
		this.lecturas = lecturas;
		this.alertas = alertas;
		this.reloj = reloj;
		this.origenSimulado = reloj.instant();
	}

	/** Al arrancar: borra la simulación anterior, precarga el historial y empieza a enviar lecturas. */
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public synchronized void iniciar() {
		alertas.deleteAll();
		lecturas.borrarSimulados();
		cambiar(propiedades.escenarioInicial());
		precargarHistorial();
		activo = true;
	}

	/** Cambia el escenario en vivo. La curva continúa desde el punto en que va; eventos y ruido son del nuevo. */
	public synchronized void cambiar(String nombre) {
		Instant ahora = reloj.instant();
		double diaActual = escenario == null ? propiedades.diaInicial() : diaEn(ahora);
		Escenario nuevo = escenarios.cargar(nombre);
		escenario = nuevo;
		ruido = new Random(nuevo.semilla());
		diaAlCambio = diaActual;
		instanteCambio = ahora;
	}

	public synchronized String escenarioActual() {
		return escenario == null ? null : escenario.nombre();
	}

	public List<String> escenariosDisponibles() {
		return escenarios.nombres();
	}

	@Scheduled(fixedDelayString = "${ecocompost.mock.intervalo-ms:5000}")
	public synchronized void enviarLectura() {
		if (!activo) {
			return;
		}
		Instant ahora = reloj.instant();
		double segundos = segundosDesde(instanteCambio, ahora);
		if (enCorte(segundos)) {
			return;
		}
		double dia = diaEn(ahora);
		double[] v = valores(dia, segundos);
		lecturas.registrar(new LecturaEntrada(propiedades.dispositivoId(), medidoEn(dia), v[0], v[1],
				OrigenLectura.SIMULADO));
	}

	private void precargarHistorial() {
		int pasos = propiedades.diasHistorial() * PASOS_HISTORIAL_POR_DIA;
		for (int i = pasos; i >= 0; i--) {
			double dia = propiedades.diaInicial() - (double) i / PASOS_HISTORIAL_POR_DIA;
			double[] v = valores(dia, -1);
			lecturas.registrar(new LecturaEntrada(propiedades.dispositivoId(), medidoEn(dia), v[0], v[1],
					OrigenLectura.SIMULADO));
		}
	}

	private double diaEn(Instant t) {
		return diaAlCambio + segundosDesde(instanteCambio, t) / escenario.segundosPorDiaSimulado();
	}

	private Instant medidoEn(double dia) {
		return origenSimulado.plus(Duration.ofMillis(Math.round((dia - propiedades.diaInicial()) * SEGUNDOS_POR_DIA * 1000)));
	}

	/** Valores de temperatura y humedad en un día de la curva, con ruido y eventos. segundos &lt; 0: sin eventos (historial). */
	private double[] valores(double dia, double segundos) {
		double[] base = interpolar(escenario.curva(), dia);
		double t = base[0] + ruido.nextGaussian() * escenario.ruido().temperaturaSigma();
		double h = base[1] + ruido.nextGaussian() * escenario.ruido().humedadSigma();
		if (segundos >= 0) {
			for (Escenario.Evento ev : escenario.eventos()) {
				if (!"rampa".equals(ev.tipo()) || segundos < ev.inicioSeg()) {
					continue;
				}
				double fraccion = ev.duracionSeg() <= 0 ? 1 : Math.min(1, (segundos - ev.inicioSeg()) / ev.duracionSeg());
				if ("temperaturaC".equals(ev.variable())) {
					t = fraccion >= 1 ? ev.hasta() : t + (ev.hasta() - t) * fraccion;
				} else if ("humedadPct".equals(ev.variable())) {
					h = fraccion >= 1 ? ev.hasta() : h + (ev.hasta() - h) * fraccion;
				}
			}
		}
		return new double[] { t, Math.max(0, Math.min(100, h)) };
	}

	private boolean enCorte(double segundos) {
		for (Escenario.Evento ev : escenario.eventos()) {
			if ("corte".equals(ev.tipo()) && segundos >= ev.inicioSeg() && segundos < ev.inicioSeg() + ev.duracionSeg()) {
				return true;
			}
		}
		return false;
	}

	/** Interpolación coseno entre puntos de control; fuera de la curva se mantiene el valor del extremo. */
	static double[] interpolar(List<Escenario.Punto> curva, double dia) {
		Escenario.Punto primero = curva.get(0);
		Escenario.Punto ultimo = curva.get(curva.size() - 1);
		if (dia <= primero.dia()) {
			return new double[] { primero.temperaturaC(), primero.humedadPct() };
		}
		if (dia >= ultimo.dia()) {
			return new double[] { ultimo.temperaturaC(), ultimo.humedadPct() };
		}
		for (int i = 0; i < curva.size() - 1; i++) {
			Escenario.Punto a = curva.get(i);
			Escenario.Punto b = curva.get(i + 1);
			if (dia >= a.dia() && dia <= b.dia()) {
				double u = (dia - a.dia()) / (b.dia() - a.dia());
				double s = (1 - Math.cos(Math.PI * u)) / 2;
				return new double[] { a.temperaturaC() + (b.temperaturaC() - a.temperaturaC()) * s,
						a.humedadPct() + (b.humedadPct() - a.humedadPct()) * s };
			}
		}
		return new double[] { ultimo.temperaturaC(), ultimo.humedadPct() };
	}

	private static double segundosDesde(Instant inicio, Instant fin) {
		return Duration.between(inicio, fin).toMillis() / 1000.0;
	}
}
