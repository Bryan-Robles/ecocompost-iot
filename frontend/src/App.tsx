import { lazy, Suspense } from 'react'
import { NavLink, Route, Routes } from 'react-router'
import Alertas from './paginas/Alertas'
import Estado from './paginas/Estado'
import Guia from './paginas/Guia'

// Historial se carga aparte: trae la librería de gráficas (Recharts), que es la parte más pesada.
// Así Estado, Guía y Alertas abren más rápido en el celular.
const Historial = lazy(() => import('./paginas/Historial'))

const secciones = [
  { nombre: 'Estado', ruta: '/' },
  { nombre: 'Historial', ruta: '/historial' },
  { nombre: 'Guía', ruta: '/guia' },
  { nombre: 'Alertas', ruta: '/alertas' },
] as const

// Navegación de los wireframes de E2 y aviso de datos simulados.
function App() {
  return (
    <>
      <div className="aviso-simulado">Datos simulados: el hardware (ESP32 + sensores) es un diseño conceptual.</div>
      <header className="cabecera">
        <strong>EcoCompost IoT</strong>
        <nav className="cabecera-nav" aria-label="Secciones">
          {secciones.map((s) => (
            <NavLink key={s.nombre} to={s.ruta} end={s.ruta === '/'}>
              {s.nombre}
            </NavLink>
          ))}
        </nav>
        <span>Casa de Jenis</span>
      </header>
      <main style={{ padding: 16 }}>
        <Routes>
          <Route path="/" element={<Estado />} />
          <Route
            path="/historial"
            element={
              <Suspense fallback={<p>Cargando el historial…</p>}>
                <Historial />
              </Suspense>
            }
          />
          <Route path="/guia" element={<Guia />} />
          <Route path="/alertas" element={<Alertas />} />
          <Route path="*" element={<Estado />} />
        </Routes>
      </main>
    </>
  )
}

export default App
