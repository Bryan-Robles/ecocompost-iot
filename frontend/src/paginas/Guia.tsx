import type { TipoAlerta } from '../api/tipos'
import { acciones } from '../contenido/acciones'
import './Guia.css'

// Guía de uso en lenguaje sencillo (M17). Producto esperado N.° 4 de la guía del curso.
// Contenido trazable a: riesgos R1–R5 y pesaje (E2), mapa de empatía y punto de vista (E1)
// y FAO (2013), Manual de compostaje del agricultor. Detalle por dato en docs/referencias.md.

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
        Los rangos y recomendaciones siguen el Manual de compostaje del agricultor de la FAO (2013). La redacción se
        ajustó con lo que dijeron Jenis, Cande y el esposo de Jenis en la prueba con usuarios.
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
            <span>
              Mira el estado en el celular, atiende las alertas, vacía la bandeja de abajo y cierra la llave si hay
              líquido en el piso.
            </span>
          </li>
          <li>
            <strong>Cande</strong>
            <span>Nada extra. Si ve líquido en el piso, solo avisar al esposo de Jenis.</span>
          </li>
        </ul>
        <p className="guia-nota">Reparto validado con Jenis y Cande en la prueba con usuarios.</p>
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
        <p>Picar los residuos en trozos de 5 a 20 cm para que se descompongan mejor.</p>
        <p className="guia-nota">
          Lista basada en los residuos pesados en la cocina durante 5 días. Los residuos orgánicos van en el recipiente
          verde (Resolución 2184 de 2019).
        </p>
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
        <p>
          <strong>Prueba del puño:</strong> con guantes, apretar un puñado de la mezcla y abrir la mano. Debe quedar
          pegado, sin escurrir agua. Si escurre, está muy húmedo; si se suelta, está seco.
        </p>
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
        <p className="guia-nota">
          En clima cálido como el de Santa Marta, la FAO recomienda agregar algo de tierra (hasta un 10 %) para que la
          compostera no se caliente demasiado.
        </p>
      </section>

      <section aria-labelledby="g-bandeja">
        <h2 id="g-bandeja">6. El líquido de abajo (bandeja)</h2>
        <p>Vaciar la bandeja una vez por semana, y también cuando el sistema avise humedad alta.</p>
        <ol className="guia-pasos">
          <li>Con guantes, poner un recipiente debajo de la llave.</li>
          <li>Abrir la llave y dejar salir el líquido.</li>
          <li>Cerrar la llave.</li>
        </ol>

        <div className="guia-caja guia-caja--rojo" role="note">
          <span className="guia-caja-icono" aria-hidden="true">✕</span>
          <div>
            <strong>No echarlo recién salido a las plantas.</strong> Puede quemarlas. Tampoco devolverlo a la
            compostera.
          </div>
        </div>

        <div className="guia-caja guia-caja--verde">
          <span className="guia-caja-icono" aria-hidden="true">✓</span>
          <div>
            <strong>Para usarlo en las plantas:</strong>
            <ol>
              <li>Guardarlo en botellas tapadas unos 15 días. Destaparlas de vez en cuando.</li>
              <li>Si ya no huele mal, mezclar 1 litro con 10 litros de agua.</li>
              <li>Regar la tierra, no las hojas. Máximo una vez cada 15 días.</li>
            </ol>
          </div>
        </div>

        <div className="guia-caja guia-caja--ambar">
          <span className="guia-caja-icono" aria-hidden="true">!</span>
          <div>
            <strong>Si sigue oscuro o con mal olor:</strong> mezclarlo con agua y botarlo por el sifón del lavaplatos,
            del lavadero o del sanitario. Nunca por la rejilla de aguas lluvias.
          </div>
        </div>

        <div className="guia-caja guia-caja--gris">
          <span className="guia-caja-icono" aria-hidden="true">?</span>
          <div>
            <strong>Si hay líquido en el piso:</strong> avisar al esposo de Jenis para que cierre la llave antes de
            limpiar.
          </div>
        </div>

        <p className="guia-nota">
          El líquido fresco puede ser tóxico para las plantas (FAO, 2013, p. 73). Recircularlo solo sirve antes de la
          etapa de calor (UAESP, 2014). Reposo y mezcla 1 en 10: Intendencia de Montevideo (2026). Botarlo por el
          sifón es una decisión del equipo.
        </p>
      </section>

      <section aria-labelledby="g-listo">
        <h2 id="g-listo">7. ¿Cuándo está listo el abono?</h2>
        <p>
          En una compostera cerrada suele llegar a la etapa de <strong>Maduración</strong> en 6 a 10 semanas, según
          el clima y los residuos. Está listo cuando:
        </p>
        <ul className="guia-pasos">
          <li>La pestaña Historial muestra la etapa Maduración.</li>
          <li>Al mezclarlo, ya no se vuelve a calentar.</li>
          <li>Se ve oscuro y huele a tierra húmeda.</li>
        </ul>
      </section>

      <section aria-labelledby="g-ayuda">
        <h2 id="g-ayuda">8. Si algo no funciona</h2>
        <p>
          Avisar al responsable técnico: <strong>Bryan Robles</strong> (equipo EcoCompost IoT).
        </p>
      </section>

      <footer className="guia-nota">
        Fuente: Román, P., Martínez, M. M. y Pantoja, A. (2013). <cite>Manual de compostaje del agricultor</cite>. FAO,
        pp. 23–73. Resolución 2184 de 2019 del Ministerio de Ambiente y Desarrollo Sostenible. UAESP y Universidad
        Nacional de Colombia (2014), <cite>Guía técnica para el aprovechamiento de residuos orgánicos</cite>.
        Intendencia de Montevideo (2026), <cite>Manual de compostaje</cite>.
      </footer>
    </article>
  )
}

export default Guia
