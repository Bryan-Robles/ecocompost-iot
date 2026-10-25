import { useEffect, useState } from 'react'
import Guia from './paginas/Guia'

const secciones = ['Estado', 'Historial', 'Guía', 'Alertas'] as const

// Provisional hasta M02 (React Router): #/guia muestra la Guía; cualquier otra ruta, Estado.
function useHash() {
  const [hash, setHash] = useState(window.location.hash)
  useEffect(() => {
    const alCambiar = () => setHash(window.location.hash)
    window.addEventListener('hashchange', alCambiar)
    return () => window.removeEventListener('hashchange', alCambiar)
  }, [])
  return hash
}

// Esqueleto inicial: navegación de los wireframes de E2 y aviso de datos simulados.
// Las pantallas se implementan en las issues M14–M17.
function App() {
  const hash = useHash()
  return (
    <>
      <div style={{ background: 'var(--ambar)', padding: '6px 16px', fontSize: 14 }}>
        Datos simulados: el hardware (ESP32 + sensores) es un diseño conceptual.
      </div>
      <header
        style={{
          display: 'flex',
          flexWrap: 'wrap',
          gap: 16,
          alignItems: 'center',
          justifyContent: 'space-between',
          padding: '12px 16px',
          borderBottom: '1px solid var(--borde)',
          background: '#fff',
        }}
      >
        <strong>EcoCompost IoT</strong>
        <nav style={{ display: 'flex', gap: 12 }}>
          {secciones.map((s) =>
            s === 'Guía' ? (
              <a key={s} href="#/guia">
                {s}
              </a>
            ) : (
              <a key={s} href="#/">
                {s}
              </a>
            ),
          )}
        </nav>
        <span>Casa de Jenis</span>
      </header>
      <main style={{ padding: 16 }}>
        {hash === '#/guia' ? (
          <Guia />
        ) : (
          <>
            <h1 style={{ color: 'var(--verde)' }}>Estado actual</h1>
            <p>Pantalla en construcción.</p>
          </>
        )}
      </main>
    </>
  )
}

export default App
