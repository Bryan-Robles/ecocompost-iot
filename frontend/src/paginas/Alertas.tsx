import { useEffect, useState } from 'react'
import { atenderAlerta, obtenerAlertas } from '../api/alertas'
import type { Alerta, Severidad } from '../api/tipos'
import './Alertas.css'

// Pantalla Alertas (M16): lista de alertas abiertas y atendidas, y detalle con
// "Qué significa / Qué hacer" (wireframe 3 de E2).

type Filtro = 'abiertas' | 'atendidas'

const etiquetaSeveridad: Record<Severidad, { icono: string; texto: string }> = {
  CRITICA: { icono: '!!', texto: 'Actuar hoy' },
  ATENCION: { icono: '!', texto: 'Revisar pronto' },
}

const formatoFecha = new Intl.DateTimeFormat('es-CO', {
  timeZone: 'America/Bogota',
  dateStyle: 'medium',
  timeStyle: 'short',
})

function fecha(iso: string) {
  return formatoFecha.format(new Date(iso))
}

function Alertas() {
  const [alertas, setAlertas] = useState<Alerta[] | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [filtro, setFiltro] = useState<Filtro>('abiertas')
  const [seleccionadaId, setSeleccionadaId] = useState<number | null>(null)
  const [guardando, setGuardando] = useState(false)

  useEffect(() => {
    obtenerAlertas()
      .then((lista) => setAlertas(lista.sort((a, b) => b.creadaEn.localeCompare(a.creadaEn))))
      .catch(() => setError('No se pudieron cargar las alertas.'))
  }, [])

  if (error) {
    return <p className="alertas-vacio">{error}</p>
  }
  if (!alertas) {
    return <p className="alertas-vacio">Cargando alertas…</p>
  }

  const abiertas = alertas.filter((a) => a.atendidaEn === null)
  const atendidas = alertas.filter((a) => a.atendidaEn !== null)
  const visibles = filtro === 'abiertas' ? abiertas : atendidas
  const seleccionada = visibles.find((a) => a.id === seleccionadaId) ?? visibles[0] ?? null

  async function marcarAtendida(id: number) {
    setGuardando(true)
    try {
      const actualizada = await atenderAlerta(id)
      setAlertas((lista) => lista?.map((a) => (a.id === id ? actualizada : a)) ?? null)
      setSeleccionadaId(null)
    } catch {
      setError('No se pudo marcar la alerta como atendida.')
    } finally {
      setGuardando(false)
    }
  }

  return (
    <div className="alertas">
      <h1>Alertas</h1>

      <div className="alertas-filtros" role="tablist" aria-label="Filtrar alertas">
        <button
          role="tab"
          aria-selected={filtro === 'abiertas'}
          className={filtro === 'abiertas' ? 'activo' : ''}
          onClick={() => {
            setFiltro('abiertas')
            setSeleccionadaId(null)
          }}
        >
          Abiertas ({abiertas.length})
        </button>
        <button
          role="tab"
          aria-selected={filtro === 'atendidas'}
          className={filtro === 'atendidas' ? 'activo' : ''}
          onClick={() => {
            setFiltro('atendidas')
            setSeleccionadaId(null)
          }}
        >
          Atendidas ({atendidas.length})
        </button>
      </div>

      {visibles.length === 0 ? (
        <p className="alertas-vacio">
          {filtro === 'abiertas'
            ? 'No hay alertas abiertas. La compostera está bien.'
            : 'Todavía no hay alertas atendidas.'}
        </p>
      ) : (
        <div className="alertas-cuerpo">
          <ul className="alertas-lista">
            {visibles.map((a) => {
              const s = etiquetaSeveridad[a.severidad]
              return (
                <li key={a.id}>
                  <button
                    className={`alertas-item${a.id === seleccionada?.id ? ' seleccionada' : ''}`}
                    aria-current={a.id === seleccionada?.id}
                    onClick={() => setSeleccionadaId(a.id)}
                  >
                    <span className={`alertas-chip alertas-chip--${a.severidad.toLowerCase()}`}>
                      <span aria-hidden="true">{s.icono}</span> {s.texto}
                    </span>
                    <strong>{a.titulo}</strong>
                    <span className="alertas-fecha">{fecha(a.creadaEn)}</span>
                  </button>
                </li>
              )
            })}
          </ul>

          {seleccionada && (
            <section className="alertas-detalle" aria-labelledby="alerta-titulo">
              <h2
                id="alerta-titulo"
                className={`alertas-banner alertas-banner--${seleccionada.severidad.toLowerCase()}`}
              >
                <span aria-hidden="true">⚠</span> {seleccionada.atendidaEn ? 'Atendida' : 'Alerta'}:{' '}
                {seleccionada.titulo}
              </h2>
              <div className="alertas-contenido">
                <h3>Qué significa</h3>
                <p>{seleccionada.queSignifica}</p>
                <h3>Qué hacer</h3>
                <ol>
                  {seleccionada.queHacer.map((paso) => (
                    <li key={paso}>{paso}</li>
                  ))}
                </ol>
                <p className="alertas-fecha">
                  Creada: {fecha(seleccionada.creadaEn)}
                  {seleccionada.atendidaEn && ` · Atendida: ${fecha(seleccionada.atendidaEn)}`}
                </p>
              </div>
              <div className="alertas-acciones">
                {!seleccionada.atendidaEn && (
                  <button
                    className="boton-primario"
                    disabled={guardando}
                    onClick={() => marcarAtendida(seleccionada.id)}
                  >
                    {guardando ? 'Guardando…' : 'Marcar como atendida'}
                  </button>
                )}
                <a className="boton-secundario" href="#/historial">
                  Ver historial
                </a>
                <a className="boton-secundario" href="#/guia">
                  Ver guía
                </a>
              </div>
            </section>
          )}
        </div>
      )}
    </div>
  )
}

export default Alertas
