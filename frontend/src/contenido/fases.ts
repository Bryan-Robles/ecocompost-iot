// Nombres de las fases en palabras de la casa (prueba con usuarios, Entregable 4, cambio 2).
// Jenis no entendió "Fase termófila"; el nombre técnico queda como dato secundario.
// Respaldo de cada fase: FAO (2013), pp. 23–24. Detalle en docs/referencias.md.

import type { Fase } from '../api/tipos'

export interface NombreFase {
  /** Nombre que ve el usuario. */
  nombre: string
  /** Qué pasa en esta etapa, en una frase. */
  descripcion: string
  /** Nombre técnico de la FAO, para quien lo quiera ver. */
  tecnico: string
}

export const fases: Record<Fase, NombreFase> = {
  MESOFILA: { nombre: 'Arranque', descripcion: 'Los residuos empiezan a descomponerse.', tecnico: 'mesófila' },
  TERMOFILA: { nombre: 'Etapa de calor', descripcion: 'La mezcla se calienta y se eliminan gérmenes.', tecnico: 'termófila' },
  ENFRIAMIENTO: { nombre: 'Enfriamiento', descripcion: 'La mezcla baja de temperatura poco a poco.', tecnico: 'enfriamiento' },
  MADURACION: { nombre: 'Maduración', descripcion: 'El abono se termina de formar.', tecnico: 'maduración' },
  DESCONOCIDA: { nombre: 'Sin determinar', descripcion: 'Todavía no hay datos suficientes.', tecnico: '' },
}

export const ordenFases: Fase[] = ['MESOFILA', 'TERMOFILA', 'ENFRIAMIENTO', 'MADURACION']
