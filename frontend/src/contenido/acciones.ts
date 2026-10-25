// Textos para el usuario de cada tipo de alerta, en lenguaje sencillo.
// Los usa la Guía (M17) y los usará la pantalla de Alertas (M16).
// Estado: borrador del equipo, pendiente de validar con Jenis y Cande (M23).
// Las cantidades (por ejemplo, 1 litro de agua) vienen del wireframe de E2 y no tienen fuente.

import type { TipoAlerta } from '../api/tipos'

export interface AccionGuia {
  titulo: string
  queSignifica: string
  queHacer: string[]
}

export const acciones: Record<TipoAlerta, AccionGuia> = {
  HUM_BAJA: {
    titulo: 'Humedad baja',
    queSignifica: 'La mezcla se está secando.',
    queHacer: ['Agregar 1 litro de agua.', 'Mezclar con una pala.', 'Revisar de nuevo en 12 horas.'],
  },
  HUM_ALTA: {
    titulo: 'Humedad alta',
    queSignifica: 'La mezcla tiene demasiada agua y puede oler mal.',
    queHacer: [
      'Agregar material seco: hojas secas o cartón sin tinta.',
      'Mezclar con una pala.',
      'Vaciar la bandeja de lixiviados (el líquido de abajo).',
    ],
  },
  TEMP_ALTA: {
    titulo: 'Temperatura alta',
    queSignifica: 'La mezcla está demasiado caliente.',
    queHacer: ['Mezclar con una pala.', 'Agregar material marrón: hojas secas o cartón sin tinta.'],
  },
  TEMP_BAJA: {
    titulo: 'Temperatura baja',
    queSignifica: 'La mezcla se enfrió antes de tiempo; puede que le falte aire.',
    queHacer: ['Mezclar con una pala para que entre aire.', 'Revisar de nuevo en 12 horas.'],
  },
  SIN_CONEXION: {
    titulo: 'Sensor sin conexión',
    queSignifica: 'El sistema no está recibiendo datos de la compostera.',
    queHacer: [
      'Revisar que el Wi-Fi de la casa funcione.',
      'No desconectar ni mojar el sensor.',
      'Avisar al responsable técnico.',
    ],
  },
}
