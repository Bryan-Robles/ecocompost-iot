// Fuente de datos de la pantalla Alertas (M16).
// Por ahora usa fixtures. Al integrar M11 se reemplaza por GET /api/alertas y
// PATCH /api/alertas/{id}/atender sin cambiar la pantalla.

import { fixtures } from '../fixtures'
import type { Alerta } from './tipos'

let alertas: Alerta[] = structuredClone(fixtures.alertas)

export async function obtenerAlertas(): Promise<Alerta[]> {
  return structuredClone(alertas)
}

export async function atenderAlerta(id: number): Promise<Alerta> {
  const alerta = alertas.find((a) => a.id === id)
  if (!alerta) {
    throw new Error(`No existe la alerta ${id}`)
  }
  const atendida = { ...alerta, atendidaEn: alerta.atendidaEn ?? new Date().toISOString() }
  alertas = alertas.map((a) => (a.id === id ? atendida : a))
  return structuredClone(atendida)
}
