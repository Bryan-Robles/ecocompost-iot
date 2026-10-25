package co.edu.unimagdalena.ecocompost.diagnostico;

/**
 * Resultado de evaluar una regla. {@code tipo} es null cuando el estado es NORMAL.
 * Los textos para el usuario ("Qué significa", "Qué hacer") se resuelven a partir del tipo (M10).
 */
public record ResultadoRegla(EstadoVariable estado, TipoAlerta tipo) {

	static final ResultadoRegla NORMAL = new ResultadoRegla(EstadoVariable.NORMAL, null);

	static ResultadoRegla atencion(TipoAlerta tipo) {
		return new ResultadoRegla(EstadoVariable.ATENCION, tipo);
	}

	static ResultadoRegla critico(TipoAlerta tipo) {
		return new ResultadoRegla(EstadoVariable.CRITICO, tipo);
	}
}
