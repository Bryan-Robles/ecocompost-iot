import { NavLink, Route, Routes } from 'react-router'
import Alertas from './paginas/Alertas'
import Estado from './paginas/Estado'
import Guia from './paginas/Guia'
import Historial from './paginas/Historial'

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
          <Route path="/historial" element={<Historial />} />
          <Route path="/guia" element={<Guia />} />
          <Route path="/alertas" element={<Alertas />} />
          <Route path="*" element={<Estado />} />
        </Routes>
      </main>
    </>
  )
}

export default App
