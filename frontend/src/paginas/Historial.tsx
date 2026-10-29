import { useEffect, useState } from 'react'
import { CartesianGrid, Line, LineChart, ReferenceArea, ReferenceLine, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts'
import { obtenerActual, obtenerDiagnostico, obtenerHistorial } from '../api/cliente'
import type { Fase, Lectura } from '../api/tipos'
import './Historial.css'

// Pantalla Historial y maduración (wireframe 2 de E2).
// El rango de 7 días se cuenta desde la última lectura: el simulador corre en tiempo acelerado,
// así que la hora de los datos no coincide con la hora del reloj.

const DIA_MS = 24 * 60 * 60 * 1000
const REFRESCO_MS = 15000

const rangos: { dias: number; etiqueta: string }[] = [
  { dias: 1, etiqueta: '1 día' },
  { dias: 3, etiqueta: '3 días' },
  { dias: 7, etiqueta: '7 días' },
  { dias: 14, etiqueta: '14 días' },
]

const fases: { id: Fase; nombre: string }[] = [
  { id: 'MESOFILA', nombre: 'Mesófila' },
  { id: 'TERMOFILA', nombre: 'Termófila' },
  { id: 'ENFRIAMIENTO', nombre: 'Enfriamiento' },
  { id: 'MADURACION', nombre: 'Maduración' },
]

const formatoHora = new Intl.DateTimeFormat('es-CO', {
  timeZone: 'America/Bogota',
  day: '2-digit',
  month: 'short',
  hour: '2-digit',
  minute: '2-digit',
})

const formatoEje = new Intl.DateTimeFormat('es-CO', { timeZone: 'America/Bogota', day: '2-digit', month: 'short' })

type Punto = { t: number; temperatura: number; humedad: number }

function Historial() {
  const [puntos, setPuntos] = useState<Punto[] | null>(null)
  const [ultima, setUltima] = useState<Lectura | null>(null)
  const [fase, setFase] = useState<Fase | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [dias, setDias] = useState(7)

  useEffect(() => {
    let cancelado = false
    async function cargar() {
      try {
        const actual = await obtenerActual()
        if (!actual) {
          if (!cancelado) setPuntos([])
          return
        }
        const hasta = new Date(actual.lectura.medidoEn)
        const desde = new Date(hasta.getTime() - dias * DIA_MS)
        const [historial, diagnostico] = await Promise.all([
          obtenerHistorial(desde, new Date(hasta.getTime() + 60_000)),
          obtenerDiagnostico(),
        ])
        if (cancelado) return
        setPuntos(
          historial.map((l) => ({
            t: new Date(l.medidoEn).getTime(),
            temperatura: l.temperaturaC,
            humedad: l.humedadPct,
          })),
        )
        setUltima(actual.lectura)
        setFase(diagnostico.fase)
        setError(null)
      } catch {
        if (!cancelado) setError('No hay conexión con el servidor. Se reintenta automáticamente.')
      }
    }
    cargar()
    const id = setInterval(cargar, REFRESCO_MS)
    return () => {
      cancelado = true
      clearInterval(id)
    }
  }, [dias])

  if (puntos === null) {
    return <p className="historial-aviso">Cargando el historial…</p>
  }

  return (
    <section className="historial">
      <h1>Historial y maduración</h1>
      <div className="historial-rango">
        <label htmlFor="historial-rango">Rango</label>
        <select id="historial-rango" value={dias} onChange={(e) => setDias(Number(e.target.value))}>
          {rangos.map((r) => (
            <option key={r.dias} value={r.dias}>
              {r.etiqueta}
            </option>
          ))}
        </select>
      </div>
      {error && <p className="historial-aviso" role="alert">{error}</p>}
      {puntos.length === 0 ? (
        <p className="historial-aviso">Todavía no hay lecturas para mostrar.</p>
      ) : (
        <>
          <Grafica
            titulo="Temperatura (°C)"
            ahora={ultima ? `${ultima.temperaturaC.toFixed(1)} °C` : ''}
            datos={puntos}
            clave="temperatura"
            dominio={[20, 80]}
            banda={[45, 65]}
            etiquetaBanda="Rango normal 45–65 °C (FAO 2013)"
          />
          <Grafica
            titulo="Humedad (%)"
            ahora={ultima ? `${ultima.humedadPct.toFixed(1)} %` : ''}
            datos={puntos}
            clave="humedad"
            dominio={[20, 80]}
            banda={[45, 60]}
            etiquetaBanda="Rango ideal 45–60 % (FAO 2013)"
          />
        </>
      )}

      <div className="historial-fases" aria-label="Fases del compostaje">
        <h2>Fases del compostaje</h2>
        <ol>
          {fases.map((f) => (
            <li key={f.id} className={f.id === fase ? 'activa' : ''} aria-current={f.id === fase ? 'step' : undefined}>
              {f.nombre}
              {f.id === fase && <small> · fase actual</small>}
            </li>
          ))}
        </ol>
      </div>

      <p className="historial-nota">
        Datos simulados para el prototipo. Los pesajes de residuos sí son reales.
      </p>
    </section>
  )
}

function Grafica(props: {
  titulo: string
  ahora: string
  datos: Punto[]
  clave: 'temperatura' | 'humedad'
  dominio: [number, number]
  banda: [number, number]
  etiquetaBanda: string
}) {
  return (
    <div className="historial-grafica">
      <div className="historial-grafica-cabecera">
        <h2>{props.titulo}</h2>
        <span>Ahora: {props.ahora}</span>
      </div>
      <ResponsiveContainer width="100%" height={220}>
        <LineChart data={props.datos} margin={{ top: 8, right: 12, bottom: 0, left: 0 }}>
          <CartesianGrid strokeDasharray="3 3" stroke="#dde3dd" />
          <ReferenceArea y1={props.banda[0]} y2={props.banda[1]} fill="#2e7d32" fillOpacity={0.08} />
          <ReferenceLine y={props.banda[0]} stroke="#2e7d32" strokeDasharray="4 4" label={{ value: props.etiquetaBanda, position: 'insideTopLeft', fontSize: 11 }} />
          <XAxis
            dataKey="t"
            type="number"
            domain={['dataMin', 'dataMax']}
            tickFormatter={(v: number) => formatoEje.format(new Date(v))}
            fontSize={12}
          />
          <YAxis domain={props.dominio} fontSize={12} width={40} />
          <Tooltip
            labelFormatter={(v) => formatoHora.format(new Date(Number(v)))}
            formatter={(valor) => [Number(valor).toFixed(1), props.titulo]}
          />
          <Line type="monotone" dataKey={props.clave} stroke="#2e7d32" dot={false} strokeWidth={2} isAnimationActive={false} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}

export default Historial
