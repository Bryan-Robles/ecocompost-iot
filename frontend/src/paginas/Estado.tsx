import { useEffect, useState } from 'react'
import { Link } from 'react-router'
import { cambiarEscenario, listarEscenarios, obtenerActual, obtenerDiagnostico } from '../api/cliente'
import type { Diagnostico, EstadoVariable, LecturaActual } from '../api/tipos'
import { fases } from '../contenido/fases'
import './Estado.css'

// Pantalla Estado (wireframe 1 de E2). Consulta la API cada 5 s.
// El selector de escenario solo aparece con ?demo=1 (modo demo, perfil mock).
// Cambios de la prueba con usuarios (E4): el estado y lo que hay que hacer van primero;
// la etiqueta (Normal / Crítico) pesa más que el número, y el número se muestra sin decimales.

const INTERVALO_MS = 5000

const textoEstado: Record<EstadoVariable, { icono: string; etiqueta: string; frase: string }> = {
  NORMAL: { icono: '✓', etiqueta: 'Normal', frase: 'está bien' },
  ATENCION: { icono: '!', etiqueta: 'Atención', frase: 'revisar pronto' },
  CRITICO: { icono: '!!', etiqueta: 'Crítico', frase: 'hay que actuar hoy' },
}

const mensajeGeneral: Record<string, { icono: string; titulo: string; detalle: string }> = {
  TODO_BIEN: { icono: '✓', titulo: 'Todo bien', detalle: 'No hay que hacer nada.' },
  ATENCION: { icono: '!', titulo: 'Revisar pronto', detalle: 'Mire los pasos de abajo.' },
  CRITICO: { icono: '!!', titulo: 'Actuar hoy', detalle: 'Siga los pasos de abajo.' },
  SIN_DATOS: { icono: '?', titulo: 'Sin datos del sensor', detalle: 'Todavía no llegan lecturas.' },
}

const sinConexionMensaje = { icono: '?', titulo: 'Sensor sin conexión', detalle: 'El aparato no está enviando datos.' }

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
  const mensaje = sinConexion ? sinConexionMensaje : mensajeGeneral[estadoGeneral]
  const pasos = diagnostico && !sinConexion ? [...new Set(diagnostico.acciones)] : []
  const hayQueActuar = sinConexion || pasos.length > 0
  const fase = diagnostico && !sinConexion && diagnostico.fase !== 'DESCONOCIDA' ? fases[diagnostico.fase] : null

  const accion = (
    <div className={`estado-accion${hayQueActuar ? ` estado-accion--${colorBanner}` : ''}`}>
      <h2>{hayQueActuar ? 'Qué hacer ahora' : 'Acción recomendada'}</h2>
      {pasos.length > 0 ? (
        <>
          <ol>
            {pasos.map((paso) => (
              <li key={paso}>{paso}</li>
            ))}
          </ol>
          <p className="estado-accion-enlace">
            <Link to="/alertas">Ver la alerta y marcarla como atendida</Link>
          </p>
        </>
      ) : (
        <p>
          {sinConexion
            ? 'Revisar el Wi-Fi de la casa. Los datos se completan cuando el sensor vuelva a enviar.'
            : 'No se requiere acción. Revisar de nuevo en 24 horas.'}
        </p>
      )}
    </div>
  )

  return (
    <section className="estado">
      <h1>Estado actual</h1>

      {error && <p className="estado-aviso" role="alert">{error}</p>}

      <div className={`estado-banner estado-banner--${colorBanner}`} role="status">
        <span className="estado-banner-icono" aria-hidden="true">
          {mensaje.icono}
        </span>
        <span>
          <strong className="estado-banner-titulo">{mensaje.titulo}</strong>
          <span className="estado-banner-detalle">{mensaje.detalle}</span>
        </span>
      </div>

      {hayQueActuar && accion}

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

      {fase && (
        <p className="estado-fase">
          <strong>{fase.nombre}:</strong> {fase.descripcion}
        </p>
      )}

      <p className="estado-ultima">
        {actual
          ? `Última lectura ${hace(actual.segundosDesdeUltima)}${sinConexion ? ' · sin conexión' : ''}`
          : 'Todavía no hay lecturas del sensor.'}
      </p>

      {!hayQueActuar && accion}

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
  const texto = props.estado ? textoEstado[props.estado] : null
  return (
    <div className={`estado-tarjeta ${clase(props.estado)}${props.desconectado ? ' estado-tarjeta--gris' : ''}`}>
      <span className="estado-tarjeta-titulo">{props.titulo}</span>
      <span className="estado-tarjeta-etiqueta">
        {props.desconectado ? (
          <>
            <span className="estado-tarjeta-icono" aria-hidden="true">?</span> Sin conexión
          </>
        ) : texto ? (
          <>
            <span className="estado-tarjeta-icono" aria-hidden="true">{texto.icono}</span> {texto.etiqueta}:{' '}
            {texto.frase}
          </>
        ) : (
          '—'
        )}
      </span>
      <span className="estado-tarjeta-valor">
        {sinValor ? '—' : Math.round(valor)}
        {!sinValor && <small> {props.unidad}</small>}
      </span>
    </div>
  )
}

export default Estado
