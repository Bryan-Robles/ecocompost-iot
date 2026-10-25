import type { TipoAlerta } from '../api/tipos'
import { acciones } from '../contenido/acciones'
import './Guia.css'

// Guía de uso en lenguaje sencillo (M17). Producto esperado N.° 4 de la guía del curso.
// Contenido trazable a: riesgos R1–R5 y pesaje (E2), mapa de empatía y punto de vista (E1).

const siSeEcha = [
  'Cáscaras de plátano, papa, yuca y zanahoria',
  'Restos crudos de frutas y verduras',
  'Cáscaras de huevo y de cítricos',
  'Residuos de café',
]

const noSeEcha = ['Carne', 'Plásticos, bolsas o empaques']

const colores = [
  { clase: 'verde', icono: '✓', nombre: 'Verde', texto: 'Todo bien. No hay que hacer nada.' },
  { clase: 'ambar', icono: '!', nombre: 'Amarillo', texto: 'Revisar pronto. Mirar la pestaña Alertas.' },
  { clase: 'rojo', icono: '!!', nombre: 'Rojo', texto: 'Actuar hoy. Seguir los pasos de la alerta.' },
  { clase: 'gris', icono: '?', nombre: 'Gris', texto: 'El sensor no envía datos. Ver "Sensor sin conexión".' },
]

const ordenAlertas: TipoAlerta[] = ['HUM_BAJA', 'HUM_ALTA', 'TEMP_ALTA', 'TEMP_BAJA', 'SIN_CONEXION']

function Guia() {
  return (
    <article className="guia">
      <h1>Guía de uso</h1>
      <p className="guia-aviso" role="note">
        Borrador del equipo. Falta revisarlo con Jenis y Cande. Las cantidades y los tiempos aún no tienen fuente
        citada.
      </p>

      <section aria-labelledby="g-quien">
        <h2 id="g-quien">1. ¿Quién hace qué?</h2>
        <ul className="guia-tarjetas">
          <li>
            <strong>Jenis</strong>
            <span>Echa los residuos de la cocina en la compostera, con la tapa bien cerrada después.</span>
          </li>
          <li>
            <strong>Esposo de Jenis</strong>
            <span>Mira el estado en el celular, atiende las alertas y vacía la bandeja de abajo.</span>
          </li>
          <li>
            <strong>Cande</strong>
            <span>Nada extra. La compostera no debe darle más trabajo de limpieza.</span>
          </li>
        </ul>
        <p className="guia-nota">Reparto propuesto por el equipo; se confirma en la prueba con usuarios.</p>
      </section>

      <section aria-labelledby="g-seguridad">
        <h2 id="g-seguridad">2. Antes de abrir la compostera</h2>
        <ol className="guia-pasos">
          <li>Ponerse guantes y tapabocas.</li>
          <li>No tocar los cables ni el sensor. Funcionan con baja tensión, pero no deben mojarse.</li>
          <li>Cerrar bien la tapa al terminar, para evitar moscas, hormigas y ratones.</li>
          <li>No dejar que niños o animales la abran sin un adulto.</li>
        </ol>
      </section>

      <section aria-labelledby="g-residuos">
        <h2 id="g-residuos">3. ¿Qué se puede echar?</h2>
        <div className="guia-columnas">
          <div className="guia-lista guia-lista--si">
            <h3>
              <span aria-hidden="true">✓</span> Sí
            </h3>
            <ul>
              {siSeEcha.map((r) => (
                <li key={r}>{r}</li>
              ))}
            </ul>
          </div>
          <div className="guia-lista guia-lista--no">
            <h3>
              <span aria-hidden="true">✕</span> No
            </h3>
            <ul>
              {noSeEcha.map((r) => (
                <li key={r}>{r}</li>
              ))}
            </ul>
          </div>
        </div>
        <p className="guia-nota">Lista basada en los residuos pesados en la cocina durante 5 días.</p>
      </section>

      <section aria-labelledby="g-colores">
        <h2 id="g-colores">4. ¿Qué significan los colores?</h2>
        <ul className="guia-colores">
          {colores.map((c) => (
            <li key={c.clase} className={`guia-color guia-color--${c.clase}`}>
              <span className="guia-color-icono" aria-hidden="true">
                {c.icono}
              </span>
              <span>
                <strong>{c.nombre}:</strong> {c.texto}
              </span>
            </li>
          ))}
        </ul>
      </section>

      <section aria-labelledby="g-alertas">
        <h2 id="g-alertas">5. ¿Qué hacer con cada alerta?</h2>
        <div className="guia-tarjetas guia-tarjetas--alertas">
          {ordenAlertas.map((tipo) => {
            const a = acciones[tipo]
            return (
              <div key={tipo} className="guia-alerta">
                <h3>{a.titulo}</h3>
                <p>{a.queSignifica}</p>
                <ol>
                  {a.queHacer.map((paso) => (
                    <li key={paso}>{paso}</li>
                  ))}
                </ol>
              </div>
            )
          })}
        </div>
      </section>

      <section aria-labelledby="g-bandeja">
        <h2 id="g-bandeja">6. La bandeja de abajo (lixiviados)</h2>
        <ol className="guia-pasos">
          <li>Con guantes, poner un recipiente debajo de la llave.</li>
          <li>Abrir la llave y dejar salir el líquido.</li>
          <li>Cerrar la llave.</li>
        </ol>
        <p className="guia-nota">Hacerlo cuando el sistema avise humedad alta o cuando la bandeja se vea llena.</p>
      </section>

      <section aria-labelledby="g-listo">
        <h2 id="g-listo">7. ¿Cuándo está listo el abono?</h2>
        <p>
          Cuando la pestaña Historial muestre la fase <strong>Maduración</strong>. El tiempo exacto depende de la mezcla
          y del clima; el equipo aún debe citar una fuente para este dato.
        </p>
      </section>

      <section aria-labelledby="g-ayuda">
        <h2 id="g-ayuda">8. Si algo no funciona</h2>
        <p>
          Avisar al responsable técnico: <strong>por definir</strong>.
        </p>
      </section>
    </article>
  )
}

export default Guia
