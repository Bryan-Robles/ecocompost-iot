// Punto único para importar los fixtures con los tipos del contrato (src/api/tipos.ts).
// Nota: `as` no valida campo por campo; revisar los JSON si cambia el contrato.
import type { Alerta, CargaResiduo, Diagnostico, Lectura, LecturaActual, Umbral } from '../api/tipos'
import alertas from './alertas.json'
import cargas from './cargas.json'
import diagnosticoAlerta from './diagnostico-humedad-baja.json'
import diagnosticoOk from './diagnostico-todo-bien.json'
import actualSinConexion from './lectura-actual-sin-conexion.json'
import actual from './lectura-actual.json'
import historial from './lecturas-7-dias.json'
import umbrales from './umbrales.json'

export const fixtures = {
  actual: actual as LecturaActual,
  actualSinConexion: actualSinConexion as LecturaActual,
  historial: historial as Lectura[],
  diagnosticoOk: diagnosticoOk as Diagnostico,
  diagnosticoAlerta: diagnosticoAlerta as Diagnostico,
  alertas: alertas as Alerta[],
  cargas: cargas as CargaResiduo[],
  umbrales: umbrales as Umbral[],
}
