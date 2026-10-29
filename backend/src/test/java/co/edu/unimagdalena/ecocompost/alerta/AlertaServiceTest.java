package co.edu.unimagdalena.ecocompost.alerta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService;
import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService.Diagnostico;
import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService.Evaluacion;
import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService.Hallazgo;
import co.edu.unimagdalena.ecocompost.diagnostico.DiagnosticoService.Medicion;
import co.edu.unimagdalena.ecocompost.diagnostico.EstadoVariable;
import co.edu.unimagdalena.ecocompost.diagnostico.Fase;
import co.edu.unimagdalena.ecocompost.diagnostico.TipoAlerta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlertaServiceTest {

	private static final Instant AHORA = Instant.parse("2026-10-28T15:00:00Z");
	private static final Hallazgo HUMEDAD_BAJA = new Hallazgo(TipoAlerta.HUM_BAJA, EstadoVariable.ATENCION);

	private AlertaRepository repositorio;
	private DiagnosticoService diagnosticos;
	private AlertaService servicio;

	@BeforeEach
	void preparar() {
		repositorio = mock(AlertaRepository.class);
		diagnosticos = mock(DiagnosticoService.class);
		Clock reloj = Clock.fixed(AHORA, ZoneOffset.UTC);
		servicio = new AlertaService(repositorio, diagnosticos, reloj);
		when(repositorio.findFirstByTipoAndAtendidaEnIsNull(any())).thenReturn(Optional.empty());
	}

	@Test
	void sinProblemasNoCreaAlertas() {
		evaluarCon(List.of());

		servicio.sincronizar();

		verify(repositorio, never()).save(any());
	}

	@Test
	void unProblemaNuevoCreaUnaAlerta() {
		evaluarCon(List.of(HUMEDAD_BAJA));

		servicio.sincronizar();

		verify(repositorio, times(1)).save(any(Alerta.class));
	}

	@Test
	void unProblemaQueSigueActivoNoDuplicaLaAlerta() {
		evaluarCon(List.of(HUMEDAD_BAJA));

		servicio.sincronizar();
		servicio.sincronizar();

		verify(repositorio, times(1)).save(any(Alerta.class));
	}

	@Test
	void unaAlertaAtendidaNoSeRepiteMientrasElProblemaSigue() {
		evaluarCon(List.of(HUMEDAD_BAJA));
		servicio.sincronizar();

		// La alerta ya fue atendida: la búsqueda de abiertas no la encuentra, pero el problema sigue activo.
		servicio.sincronizar();

		verify(repositorio, times(1)).save(any(Alerta.class));
	}

	@Test
	void unProblemaQueVuelveDespuesDeDesaparecerCreaOtraAlerta() {
		evaluarCon(List.of(HUMEDAD_BAJA));
		servicio.sincronizar();
		evaluarCon(List.of());
		servicio.sincronizar();

		evaluarCon(List.of(HUMEDAD_BAJA));
		servicio.sincronizar();

		verify(repositorio, times(2)).save(any(Alerta.class));
	}

	@Test
	void atenderMarcaLaAlertaConLaHoraActual() {
		Alerta alerta = new Alerta(TipoAlerta.HUM_BAJA, Severidad.ATENCION, "Humedad baja (40 %)",
				"La mezcla se está secando.", "Agregar agua.", AHORA.minusSeconds(60));
		when(repositorio.findById(7L)).thenReturn(Optional.of(alerta));

		Alerta atendida = servicio.atender(7L);

		assertThat(atendida.getAtendidaEn()).isEqualTo(AHORA);
	}

	private void evaluarCon(List<Hallazgo> hallazgos) {
		Diagnostico d = new Diagnostico(new Medicion(40.0, EstadoVariable.NORMAL),
				new Medicion(40.0, EstadoVariable.ATENCION), Fase.TERMOFILA, "ATENCION", List.of(), false,
				"EN_LINEA", 0);
		when(diagnosticos.evaluar()).thenReturn(new Evaluacion(d, hallazgos));
	}
}
