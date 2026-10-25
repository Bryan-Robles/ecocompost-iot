// Contrato de la API REST de EcoCompost IoT (M04).
// Fuente: docs/analisis-arquitectura-entregable-3.md, sección 2.5.
// Las fechas viajan en ISO-8601 UTC; la UI las muestra en America/Bogota.

export type OrigenLectura = 'SIMULADO' | 'MANUAL' | 'ESP32'

export type EstadoVariable = 'NORMAL' | 'ATENCION' | 'CRITICO'

export type EstadoGeneral = 'TODO_BIEN' | 'ATENCION' | 'CRITICO' | 'SIN_DATOS'

export type Fase = 'MESOFILA' | 'TERMOFILA' | 'ENFRIAMIENTO' | 'MADURACION' | 'DESCONOCIDA'

export type EstadoConexion = 'EN_LINEA' | 'SIN_CONEXION'

export type TipoAlerta = 'TEMP_ALTA' | 'TEMP_BAJA' | 'HUM_BAJA' | 'HUM_ALTA' | 'SIN_CONEXION'

export type Severidad = 'ATENCION' | 'CRITICA'

/** Cuerpo de POST /api/lecturas (mismo contrato para el ESP32 y el simulador). */
export interface LecturaEntrada {
  dispositivoId: string
  medidoEn: string
  temperaturaC: number
  humedadPct: number
  origen?: OrigenLectura
}

/** Elemento de GET /api/lecturas?desde=&hasta= */
export interface Lectura extends LecturaEntrada {
  id: number
  origen: OrigenLectura
  recibidoEn: string
}

/** Respuesta de GET /api/lecturas/actual */
export interface LecturaActual {
  lectura: Lectura
  recibidoEn: string
  conexion: EstadoConexion
  segundosDesdeUltima: number
}

export interface EstadoMedicion {
  valor: number
  estado: EstadoVariable
}

/** Respuesta de GET /api/diagnostico */
export interface Diagnostico {
  temperatura: EstadoMedicion
  humedad: EstadoMedicion
  fase: Fase
  estadoGeneral: EstadoGeneral
  acciones: string[]
  umbralesConFuentePendiente: boolean
}

/** Elemento de GET /api/alertas */
export interface Alerta {
  id: number
  tipo: TipoAlerta
  severidad: Severidad
  titulo: string
  queSignifica: string
  queHacer: string[]
  creadaEn: string
  atendidaEn: string | null
}

/** Elemento de GET /api/cargas */
export interface CargaResiduo {
  id: number
  /** null mientras no se registre la fecha real del pesaje */
  fecha: string | null
  pesoKg: number
  tipoResiduo: string
  origen: 'PESAJE_REAL' | 'SIMULADO'
}

/** Elemento de GET /api/umbrales */
export interface Umbral {
  clave: string
  valor: number
  unidad: string
  fuente: { clave: string; referencia: string; url?: string; estado: 'VERIFICADA' | 'CRITERIO_EQUIPO' | 'PENDIENTE' } | null
}
