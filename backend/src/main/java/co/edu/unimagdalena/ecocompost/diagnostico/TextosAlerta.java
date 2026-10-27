package co.edu.unimagdalena.ecocompost.diagnostico;

import java.util.List;

/**
 * Textos para el usuario de cada alerta. Son los mismos de frontend/src/contenido/acciones.ts:
 * si cambian, cambiar los dos archivos.
 */
public final class TextosAlerta {

	public record Texto(String queSignifica, List<String> queHacer) {
	}

	private TextosAlerta() {
	}

	public static Texto de(TipoAlerta tipo) {
		return switch (tipo) {
			case HUM_BAJA -> new Texto("La mezcla se está secando y el proceso se puede detener.", List.of(
					"Agregar agua poco a poco, o restos de fruta y verdura.",
					"Mezclar con una pala.",
					"Hacer la prueba del puño: el material debe quedar pegado sin escurrir agua."));
			case HUM_ALTA -> new Texto("La mezcla tiene demasiada agua: le falta aire y puede oler mal.", List.of(
					"Mezclar con una pala.",
					"Agregar material seco: hojas secas, paja o aserrín.",
					"Vaciar la bandeja de lixiviados (el líquido de abajo)."));
			case TEMP_ALTA -> new Texto("La mezcla está demasiado caliente y el proceso se frena.", List.of(
					"Mezclar con una pala.",
					"Hacer la prueba del puño para revisar la humedad.",
					"Agregar material seco que tarde en descomponerse: pasto seco o astillas de madera."));
			case TEMP_BAJA -> new Texto("La mezcla se enfrió antes de tiempo.", List.of(
					"Hacer la prueba del puño: si está seca, agregar agua o restos de fruta y verdura.",
					"Si hay poco material, agregar más residuos de cocina.",
					"Revisar de nuevo al día siguiente."));
			case SIN_CONEXION -> new Texto("El sistema no está recibiendo datos de la compostera.", List.of(
					"Revisar que el Wi-Fi de la casa funcione.",
					"No desconectar ni mojar el sensor.",
					"Avisar al responsable técnico (Bryan Robles)."));
		};
	}
}
