// Cliente de la API del prototipo.
// Con VITE_MODO=offline (plan B de la demo) responde con los fixtures, sin backend.

import { fixtures } from '../fixtures'
import type { Alerta, CargaResiduo, Diagnostico, Lectura, LecturaActual, Umbral } from './tipos'

const offline = import.meta.env.VITE_MODO === 'offline'

async function pedir<T>(ruta: string, opciones?: RequestInit): Promise<T> {
  const respuesta = await fetch(ruta, {
    ...opciones,
    headers: { 'Content-Type': 'application/json', ...opciones?.headers },
  })
  if (!respuesta.ok) {
    const cuerpo = await respuesta.json().catch(() => null)
    throw new Error(cuerpo?.error ?? `Error ${respuesta.status} en ${ruta}`)
  }
  if (respuesta.status === 204) {
    return null as T
  }
  return (await respuesta.json()) as T
}

/** Hace que los fixtures se comporten como una respuesta de la API (copia para no mutar). */
function copia<T>(valor: T): Promise<T> {
  return Promise.resolve(structuredClone(valor))
}

export function obtenerActual(): Promise<LecturaActual | null> {
  return offline ? copia(fixtures.actual) : pedir<LecturaActual | null>('/api/lecturas/actual')
}

export function obtenerDiagnostico(): Promise<Diagnostico> {
  return offline ? copia(fixtures.diagnosticoOk) : pedir<Diagnostico>('/api/diagnostico')
}

export function obtenerHistorial(desde: Date, hasta: Date): Promise<Lectura[]> {
  if (offline) {
    return copia(fixtures.historial)
  }
  const q = new URLSearchParams({ desde: desde.toISOString(), hasta: hasta.toISOString() })
  return pedir<Lectura[]>(`/api/lecturas?${q}`)
}

export function obtenerAlertas(estado: 'abiertas' | 'atendidas' | 'todas' = 'todas'): Promise<Alerta[]> {
  if (offline) {
    return copia(fixtures.alertas)
  }
  return pedir<Alerta[]>(`/api/alertas?estado=${estado}`)
}

export function atenderAlerta(id: number): Promise<Alerta> {
  if (offline) {
    const alerta = fixtures.alertas.find((a) => a.id === id)
    if (!alerta) {
      return Promise.reject(new Error(`No existe la alerta ${id}`))
    }
    return copia({ ...alerta, atendidaEn: alerta.atendidaEn ?? new Date().toISOString() })
  }
  return pedir<Alerta>(`/api/alertas/${id}/atender`, { method: 'PATCH' })
}

export function obtenerCargas(): Promise<CargaResiduo[]> {
  return offline ? copia(fixtures.cargas) : pedir<CargaResiduo[]>('/api/cargas')
}

export function obtenerUmbrales(): Promise<Umbral[]> {
  return offline ? copia(fixtures.umbrales) : pedir<Umbral[]>('/api/umbrales')
}

/** Solo disponible con el perfil mock (modo demo). */
export function listarEscenarios(): Promise<{ actual: string; disponibles: string[] }> {
  if (offline) {
    return Promise.resolve({ actual: 'todo-bien', disponibles: [] })
  }
  return pedir('/api/demo/escenarios')
}

export function cambiarEscenario(nombre: string): Promise<{ actual: string }> {
  return pedir('/api/demo/escenario', { method: 'POST', body: JSON.stringify({ nombre }) })
}

export const modoOffline = offline
