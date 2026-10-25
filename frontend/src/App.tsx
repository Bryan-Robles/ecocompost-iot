const secciones = ['Estado', 'Historial', 'Guía', 'Alertas'] as const

// Esqueleto inicial: navegación de los wireframes de E2 y aviso de datos simulados.
// Las pantallas se implementan en las issues M14–M17.
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
            <span key={s}>{s}</span>
          ))}
        </nav>
        <span>Casa de Jenis</span>
      </header>
      <main style={{ padding: 16 }}>
        <h1 style={{ color: 'var(--verde)' }}>Estado actual</h1>
        <p>Pantalla en construcción.</p>
      </main>
    </>
  )
}

export default App
