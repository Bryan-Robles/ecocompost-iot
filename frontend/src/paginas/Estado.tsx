import { useEffect, useState } from 'react'
import { cambiarEscenario, listarEscenarios, obtenerActual, obtenerDiagnostico } from '../api/cliente'
import type { Diagnostico, EstadoVariable, Fase, LecturaActual } from '../api/tipos'
import './Estado.css'

// Pantalla Estado (wireframe 1 de E2). Consulta la API cada 5 s.
// El selector de escenario solo aparece con ?demo=1 (modo demo, perfil mock).

const INTERVALO_MS = 5000

const nombreFase: Record<Fase, string> = {
  MESOFILA: 'Mesófila',
  TERMOFILA: 'Termófila',
  ENFRIAMIENTO: 'Enfriamiento',
  MADURACION: 'Maduración',
  DESCONOCIDA: 'Sin determinar',
}

const textoEstado: Record<EstadoVariable, string> = {
  NORMAL: 'Normal',
  ATENCION: 'Atención',
  CRITICO: 'Crítico',
}

const mensajeGeneral: Record<string, string> = {
  TODO_BIEN: 'Todo bien',
  ATENCION: 'Revisar pronto',
  CRITICO: 'Actuar hoy',
  SIN_DATOS: 'Sin datos del sensor',
}

function hace(segundos: number) {
  if (segundos < 60) return `hace ${segundos} s`
  const minutos = Math.floor(segundos / 60)
  return `hace ${minutos} min`
}

function clase(estado: EstadoVariable | undefined) {
  return `estado-${(estado ?? 'NORMAL').toLowerCase()}`
}

function Estado() {
  const [actual, setActual] = useState<LecturaActual | null | undefined>(undefined)
  const [diagnostico, setDiagnostico] = useState<Diagnostico | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [escenarios, setEscenarios] = useState<{ actual: string; disponibles: string[] } | null>(null)
  const demo = new URLSearchParams(window.location.search).get('demo') === '1'

  useEffect(() => {
    let cancelado = false
    async function cargar() {
      try {
        const [a, d] = await Promise.all([obtenerActual(), obtenerDiagnostico()])
        if (!cancelado) {
          setActual(a)
          setDiagnostico(d)
          setError(null)
        }
      } catch {
        if (!cancelado) setError('No hay conexión con el servidor. Se reintenta automáticamente.')
      }
    }
    cargar()
    const id = setInterval(cargar, INTERVALO_MS)
    return () => {
      cancelado = true
      clearInterval(id)
    }
  }, [])

  useEffect(() => {
    if (demo) {
      listarEscenarios().then(setEscenarios).catch(() => setEscenarios(null))
    }
  }, [demo])

  async function elegir(nombre: string) {
    try {
      await cambiarEscenario(nombre)
      setEscenarios((e) => (e ? { ...e, actual: nombre } : e))
    } catch {
      setError('No se pudo cambiar el escenario.')
    }
  }

  if (actual === undefined) {
    return error ? (
      <p className="estado-aviso" role="alert">{error}</p>
    ) : (
      <p className="estado-aviso">Cargando el estado de la compostera…</p>
    )
  }

  const sinConexion = actual?.conexion === 'SIN_CONEXION' || actual === null
  const estadoGeneral = diagnostico?.estadoGeneral ?? 'SIN_DATOS'
  const colorBanner = sinConexion ? 'gris' : estadoGeneral === 'CRITICO' ? 'rojo' : estadoGeneral === 'ATENCION' ? 'ambar' : 'verde'

  return (
    <section className="estado">
      <h1>Estado actual</h1>

      {error && <p className="estado-aviso" role="alert">{error}</p>}

      <div className={`estado-banner estado-banner--${colorBanner}`} role="status">
        <strong>Estado del proceso:</strong>{' '}
        {sinConexion ? 'Sensor sin conexión' : mensajeGeneral[estadoGeneral]}
        {diagnostico && !sinConexion && <> · Fase {nombreFase[diagnostico.fase].toLowerCase()}</>}
      </div>

      <div className="estado-tarjetas">
        <Tarjeta
          titulo="Temperatura"
          valor={diagnostico?.temperatura.valor}
          unidad="°C"
          estado={diagnostico?.temperatura.estado}
          desconectado={sinConexion}
        />
        <Tarjeta
          titulo="Humedad"
          valor={diagnostico?.humedad.valor}
          unidad="%"
          estado={diagnostico?.humedad.estado}
          desconectado={sinConexion}
        />
      </div>

      <p className="estado-ultima">
        {actual
          ? `Última lectura ${hace(actual.segundosDesdeUltima)}${sinConexion ? ' · sin conexión' : ''}`
          : 'Todavía no hay lecturas del sensor.'}
      </p>

      <div className="estado-accion">
        <h2>Acción recomendada</h2>
        {diagnostico && diagnostico.acciones.length > 0 && !sinConexion ? (
          <ol>
            {[...new Set(diagnostico.acciones)].map((paso) => (
              <li key={paso}>{paso}</li>
            ))}
          </ol>
        ) : (
          <p>
            {sinConexion
              ? 'Revisar el Wi-Fi de la casa. Los datos se completan cuando el sensor vuelva a enviar.'
              : 'No se requiere acción. Revisar de nuevo en 24 horas.'}
          </p>
        )}
      </div>

      {demo && escenarios && (
        <div className="estado-demo">
          <label>
            Escenario de demo:{' '}
            <select value={escenarios.actual} onChange={(e) => elegir(e.target.value)}>
              {escenarios.disponibles.map((n) => (
                <option key={n} value={n}>
                  {n}
                </option>
              ))}
            </select>
          </label>
        </div>
      )}
    </section>
  )
}

function Tarjeta(props: {
  titulo: string
  valor: number | null | undefined
  unidad: string
  estado: EstadoVariable | undefined
  desconectado: boolean
}) {
  const valor = props.valor
  const sinValor = valor === null || valor === undefined || props.desconectado
  return (
    <div className={`estado-tarjeta ${clase(props.estado)}${props.desconectado ? ' estado-tarjeta--gris' : ''}`}>
      <span className="estado-tarjeta-titulo">{props.titulo}</span>
      <span className="estado-tarjeta-valor">
        {sinValor ? '—' : valor.toFixed(1)}
        {!sinValor && <small> {props.unidad}</small>}
      </span>
      <span className="estado-tarjeta-etiqueta">
        {props.desconectado ? 'Sin conexión' : props.estado ? textoEstado[props.estado] : '—'}
      </span>
    </div>
  )
}

export default Estado
