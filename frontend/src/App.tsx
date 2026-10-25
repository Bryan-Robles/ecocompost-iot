import { useEffect, useState } from 'react'
import Alertas from './paginas/Alertas'
import Guia from './paginas/Guia'

const secciones = [
  { nombre: 'Estado', ruta: '#/' },
  { nombre: 'Historial', ruta: '#/historial' },
  { nombre: 'Guía', ruta: '#/guia' },
  { nombre: 'Alertas', ruta: '#/alertas' },
] as const

// Provisional hasta M02 (React Router): #/guia y #/alertas tienen pantalla; el resto muestra Estado.
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
          {secciones.map((s) => (
            <a key={s.nombre} href={s.ruta} aria-current={hash === s.ruta ? 'page' : undefined}>
              {s.nombre}
            </a>
          ))}
        </nav>
        <span>Casa de Jenis</span>
      </header>
      <main style={{ padding: 16 }}>
        {hash === '#/guia' ? (
          <Guia />
        ) : hash === '#/alertas' ? (
          <Alertas />
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
