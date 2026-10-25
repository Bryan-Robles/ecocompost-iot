// Textos para el usuario de cada tipo de alerta, en lenguaje sencillo.
// Los usa la Guía (M17) y los usará la pantalla de Alertas (M16).
// Respaldo: Tablas 2 y 3 y técnica del puño de FAO (2013), pp. 27, 28 y 56. Detalle en docs/referencias.md.
// Pendiente: validar la redacción con Jenis y Cande (M23).

import type { TipoAlerta } from '../api/tipos'

export interface AccionGuia {
  titulo: string
  queSignifica: string
  queHacer: string[]
}

export const acciones: Record<TipoAlerta, AccionGuia> = {
  HUM_BAJA: {
    titulo: 'Humedad baja',
    queSignifica: 'La mezcla se está secando y el proceso se puede detener.',
    queHacer: [
      'Agregar agua poco a poco, o restos de fruta y verdura.',
      'Mezclar con una pala.',
      'Hacer la prueba del puño: el material debe quedar pegado sin escurrir agua.',
    ],
  },
  HUM_ALTA: {
    titulo: 'Humedad alta',
    queSignifica: 'La mezcla tiene demasiada agua: le falta aire y puede oler mal.',
    queHacer: [
      'Mezclar con una pala.',
      'Agregar material seco: hojas secas, paja o aserrín.',
      'Vaciar la bandeja de lixiviados (el líquido de abajo).',
    ],
  },
  TEMP_ALTA: {
    titulo: 'Temperatura alta',
    queSignifica: 'La mezcla está demasiado caliente y el proceso se frena.',
    queHacer: [
      'Mezclar con una pala.',
      'Hacer la prueba del puño para revisar la humedad.',
      'Agregar material seco que tarde en descomponerse: pasto seco o astillas de madera.',
    ],
  },
  TEMP_BAJA: {
    titulo: 'Temperatura baja',
    queSignifica: 'La mezcla se enfrió antes de tiempo.',
    queHacer: [
      'Hacer la prueba del puño: si está seca, agregar agua o restos de fruta y verdura.',
      'Si hay poco material, agregar más residuos de cocina.',
      'Revisar de nuevo al día siguiente.',
    ],
  },
  SIN_CONEXION: {
    titulo: 'Sensor sin conexión',
    queSignifica: 'El sistema no está recibiendo datos de la compostera.',
    queHacer: [
      'Revisar que el Wi-Fi de la casa funcione.',
      'No desconectar ni mojar el sensor.',
      'Avisar al responsable técnico (Bryan Robles).',
    ],
  },
}
