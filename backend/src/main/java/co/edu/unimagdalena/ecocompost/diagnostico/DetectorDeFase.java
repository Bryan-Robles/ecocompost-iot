package co.edu.unimagdalena.ecocompost.diagnostico;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import co.edu.unimagdalena.ecocompost.lectura.Lectura;

import org.springframework.stereotype.Component;

/**
 * Detecta la fase del compostaje (máquina de estados de la sección 4.4 del análisis).
 *
 * <p>La máquina avanza lectura por lectura. Por eso no depende de una ventana fija de días:
 * el estado (fase, racha de enfriamiento y últimas temperaturas) se conserva entre lecturas.
 *
 * <p>Media móvil: la temperatura se suaviza con el promedio de las últimas {@code ventana-lecturas}.
 * Histéresis: un cambio de fase solo se confirma si la media móvil cumple la condición
 * durante toda la ventana. Un pico aislado o una bajada corta no cambian la fase.
 */
@Component
public class DetectorDeFase {

	private final UmbralesProperties.Temperatura temperatura;
	private final FaseProperties fase;

	public DetectorDeFase(UmbralesProperties umbrales, FaseProperties fase) {
		this.temperatura = umbrales.temperatura();
		this.fase = fase;
	}

	/** Seguimiento nuevo, en MESOFILA y sin historia. */
	public Seguimiento nuevoSeguimiento() {
		return new Seguimiento();
	}

	/**
	 * @param historial lecturas ordenadas por hora de medición, hasta la lectura actual inclusive
	 * @return la fase al final del historial; DESCONOCIDA si no hay lecturas
	 */
	public Fase detectar(List<Lectura> historial) {
		if (historial.isEmpty()) {
			return Fase.DESCONOCIDA;
		}
		Seguimiento seguimiento = nuevoSeguimiento();
		historial.forEach(seguimiento::avanzar);
		return seguimiento.fase();
	}

	/** Estado de la máquina de fases. Se alimenta con las lecturas en orden. */
	public class Seguimiento {

		private final Deque<Double> temperaturas = new ArrayDeque<>();
		private final Deque<Double> medias = new ArrayDeque<>();
		private Fase estado = Fase.MESOFILA;
		private Instant inicioFrio;

		public void avanzar(Lectura l) {
			int n = fase.ventanaLecturas();
			double limiteFrio = fase.temperaturaAmbiente() + fase.margenSobreAmbiente();
			agregar(temperaturas, l.getTemperaturaC(), n);
			agregar(medias, promedio(temperaturas), n);
			double media = medias.peekLast();
			boolean ventanaCompleta = medias.size() == n;
			boolean termofilaSostenida = ventanaCompleta
					&& medias.stream().allMatch(m -> m >= temperatura.normalMin());
			boolean enfriamientoSostenido = ventanaCompleta
					&& medias.stream().allMatch(m -> m < temperatura.normalMin())
					&& media < medias.peekFirst();

			if (estado != Fase.ENFRIAMIENTO || media > limiteFrio) {
				inicioFrio = null;
			} else if (inicioFrio == null) {
				inicioFrio = l.getMedidoEn();
			}

			switch (estado) {
				case MESOFILA -> {
					if (termofilaSostenida) {
						estado = Fase.TERMOFILA;
					}
				}
				case TERMOFILA -> {
					if (enfriamientoSostenido) {
						estado = Fase.ENFRIAMIENTO;
					}
				}
				case ENFRIAMIENTO -> {
					if (termofilaSostenida) {
						estado = Fase.TERMOFILA;
					} else if (inicioFrio != null
							&& Duration.between(inicioFrio, l.getMedidoEn()).compareTo(diasMinimos()) >= 0) {
						estado = Fase.MADURACION;
					}
				}
				default -> {
					// MADURACION solo vuelve a MESOFILA con una carga nueva; el prototipo no registra cargas.
				}
			}
		}

		public Fase fase() {
			return estado;
		}
	}

	private Duration diasMinimos() {
		return Duration.ofDays(fase.diasMinimosMaduracion());
	}

	private static void agregar(Deque<Double> cola, double valor, int tamanio) {
		cola.addLast(valor);
		if (cola.size() > tamanio) {
			cola.removeFirst();
		}
	}

	private static double promedio(Deque<Double> valores) {
		return valores.stream().mapToDouble(Double::doubleValue).average().orElseThrow();
	}
}
