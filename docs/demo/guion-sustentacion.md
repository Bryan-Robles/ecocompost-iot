# Guion de la sustentación final · Entregable 5

**Informe final y sustentación** (actividad evaluativa N.° 4) · Viernes 13 de noviembre de 2026 (o 20 de noviembre, según el turno) · 15 min de presentación con demo + 5 min de preguntas

Rúbrica: Claridad y coherencia 20 % (las 5 etapas con evidencia) · Originalidad 30 % (la iteración tras la prueba mejora la solución) · Aplicabilidad 30 % (propuesta con costos, responsables, guía e impacto con datos propios) · Presentación y orden 20 % (todos responden).

**Público:** la docente y compañeros. Se habla de la compostera y de las personas de la casa; la tecnología se nombra en una frase cuando haga falta.

## Mensaje central (al inicio y al cierre)

> "En la cocina de Jenis salen unos 3,9 kilos de residuos orgánicos al día. EcoCompost vigila la compostera y le dice a la familia, con palabras sencillas y colores, qué hacer y cuándo. Después de probarlo con ellos, lo hicimos más fácil de leer y de usar, y proponemos instalarlo primero en la casa y luego en otras pensiones del barrio."

## Guion minuto a minuto

| Min | Quién | Bloque | Qué se dice o se muestra | Criterio |
|---|---|---|---|---|
| 0:00–1:30 | Bryan | **Empatizar** | La casa: dueños y 10 estudiantes. Entrevistamos a 5 personas. Lo que no sabíamos: el dolor más fuerte es el trabajo de Cande con las bolsas que escurren, no solo el olor. | Claridad |
| 1:30–2:30 | Karyme | **Definir** | Punto de vista y pregunta «¿Cómo podríamos…?». Pesaje propio: 19,5 kg en 5 días (3,9 kg/día). | Claridad |
| 2:30–4:00 | Karyme | **Idear** | 8 ideas en Crazy 8s y matriz de impacto y esfuerzo. Elegimos la compostera aeróbica; el biodigestor se descartó por gas a presión y olores. Cambios por fuentes: bidón de 50 a 220 L (FAO) y se retiraron datos sin fuente. | Originalidad, Claridad |
| 4:00–5:30 | Jussi | **Prototipar** | Esquema: sensores → ESP32 → servidor → app. Alcance autorizado: el software funciona; el hardware es un diseño y los datos de temperatura y humedad son simulados (aviso visible en la app). Valores de la FAO: 45–65 °C y 45–60 %. | Aplicabilidad, Claridad |
| 5:30–7:00 | Bryan | **Evaluar** | Prueba por videollamada con Jenis, Cande y el esposo: 6 hipótesis. Se cumplieron H1, H2, H4 y H6; H5 con mejoras; **H3 no** (qué hacer con el líquido de abajo). Mostrar la tabla de 6 cambios. | Originalidad |
| 7:00–8:00 | Jussi | **Demo 1 · Antes y después** | Pantalla **Estado** en verde: mensaje grande «Todo bien», etiqueta antes del número, menú en pestañas. Mostrar al lado la captura vieja (Figura 2 del informe). | Originalidad |
| 8:00–9:30 | Jussi | **Demo 2 · Alerta** | Escenario **humedad-baja** (activarlo en el minuto 6, ver nota). La pantalla pasa a rojo y muestra «Qué hacer ahora» primero. Abrir **Alertas** y marcarla como atendida. | Aplicabilidad |
| 9:30–10:30 | Karyme | **Demo 3 · Guía** | Pestaña **Guía**, sección 6 «El líquido de abajo»: no echarlo fresco, reposo de 15 días, 1 en 10, y qué hacer si huele mal. Cada paso tiene fuente (FAO, UAESP, Intendencia de Montevideo). Mostrar «¿Quién hace qué?»: Cande, nada extra. | Originalidad, Aplicabilidad |
| 10:30–11:00 | Karyme | **Demo 4 · Etapas** | Pestaña **Historial**: etapas con nombres sencillos (Arranque, Etapa de calor, Enfriamiento, Maduración). | Aplicabilidad |
| 11:00–13:30 | Bryan | **Propuesta** | Fase 1: piloto de 12 semanas en la casa. Fase 2: otras pensiones del barrio. Costo de una unidad: 580.140 COP; el servidor es gratuito (Oracle Cloud Always Free). Responsables: el esposo de Jenis opera, Jenis deposita, Cande nada extra, Bryan es el contacto técnico. | Aplicabilidad |
| 13:30–15:00 | Bryan | **Impacto y cierre** | Un bidón se llena en 22 días: ≈ 86 kg menos al camión y 26–51 kg de abono por ciclo. Para aprovechar los ≈ 117 kg/mes de la casa hay que sumar bidones y coordinar llenado, rotación y días del camión. Son proyecciones con el pesaje propio. Repetir el mensaje central. | Aplicabilidad, Claridad |
| 15:00–20:00 | Todos | **Preguntas** | Cada uno responde las que preparó (abajo). | Presentación |

**Nota de la demo 2:** el escenario `humedad-baja` tarda entre 1,5 y 2 minutos en llegar a rojo, y después se recupera solo. Activarlo cuando empiece el bloque «Evaluar» (minuto 5:30), con el selector de `?demo=1` o con el comando del anexo. Si se pasa el momento, volver a activarlo.

## Preguntas de la guía del curso

| Pregunta | Responde | Respuesta corta |
|---|---|---|
| ¿Quiénes son sus usuarios y qué descubrieron que no sabían? | Bryan | Jenis, Cande, el esposo de Jenis y los estudiantes. Descubrimos que el mayor dolor es el trabajo físico de Cande, y en la prueba, que los términos técnicos los confunden y que nadie sabía qué hacer con el líquido de abajo. |
| ¿Por qué compostera y no biodigestor? ¿Qué descartaron? | Karyme | La compostera aeróbica no produce gas a presión ni olores fuertes y da abono para la terraza. Descartamos el biodigestor, la compostera manual giratoria y, para más adelante, la automatización con válvulas y la visión artificial. |
| ¿Qué valores usan para decidir si el proceso va bien, y de dónde salen? | Karyme | FAO (2013): temperatura normal 45–65 °C, problema sobre 70 °C o bajo 35 °C; humedad ideal 45–60 %. El margen de aviso de 5 puntos y los 14 días de enfriamiento prematuro son criterio del equipo, y así lo decimos. |
| ¿Qué cambió entre el primer prototipo y el final, y por qué? | Jussi | De los mockups de E2 al prototipo: bidón de 50 a 220 L por el cálculo de la FAO. Después de la prueba: 6 cambios en la app, cada uno con evidencia (letra, vocabulario, números, instrucciones, pantallas, colores). |
| ¿Cuántos residuos se dejarían de enviar a la recolección y cómo lo calcularon? | Bryan | ≈ 86 kg por ciclo: 3,9 kg/día del pesaje propio × 22 días, que es lo que tarda en llenarse el bidón. Después madura de 6 a 10 semanas. Con más bidones y una estrategia coordinada con el camión se llegaría a ≈ 117 kg/mes. |
| ¿Quién operará el sistema después del curso? | Jussi | El esposo de Jenis, que revisa la app cada mañana; Jenis deposita los residuos; Cande no hace trabajo extra. Bryan queda como contacto técnico. |

## Preguntas difíciles probables

- **"¿Los datos son reales?"** El pesaje sí es real. La temperatura y la humedad son simuladas porque el hardware es un diseño; así lo autorizó la docente y la app lo dice en todas las pantallas.
- **"¿Por qué no se cumplió H3 y qué hicieron?"** El esposo diluiría el líquido, pero no sabía que debe reposar antes. La Guía no lo decía. Investigamos (FAO, UAESP, Montevideo) y agregamos la sección 6 con fuente en cada paso. Se volverá a probar en el piloto.
- **"¿El lixiviado sirve o no sirve?"** Tiene nutrientes, pero fresco puede dañar las plantas (FAO, p. 73). Después de reposar y diluirse 1 en 10 se puede regar en la tierra cada 15 días (Intendencia de Montevideo, 2026).
- **"¿Por qué se bota por el sifón?"** Es una decisión del equipo: ninguna fuente dice cómo desechar el que no sirve. Se diluye y va al alcantarillado sanitario, nunca a la rejilla de aguas lluvias.
- **"¿Cuándo se pesó?"** Los días 1 a 3 se pesaron para el Entregable 1, y el pesaje continuó hasta completar los 5 días que se reportaron en el Entregable 2.
- **"¿El costo está completo?"** Sí: 580.140 COP por unidad, con todos los materiales cotizados en tiendas colombianas. El ESP32 y el adaptador estaban agotados el día de la consulta, así que su precio es de referencia; la resina alcanza para varias unidades.
- **"¿Dónde corre el servidor?"** En una máquina virtual gratuita de Oracle Cloud (Always Free), que guarda la base de datos en disco. Si Oracle la recupera por poco uso, la alternativa es el nivel gratuito de Google Cloud.
- **"¿La lista de riesgos fue aprobada?"** Sí, la docente la aprobó. Son 6 riesgos (R1 a R6) con su control.
- **"¿De dónde sale la temperatura ambiente del simulador?"** Son 30 °C, la temperatura promedio de Santa Marta que eligió el equipo.

## Lista de chequeo

**Día anterior:**

- [ ] Dos ensayos completos con cronómetro (máximo 15 min)
- [ ] Informe final en PDF entregado y coevaluación diligenciada
- [ ] Probar el modo offline (`VITE_MODO=offline npm run dev`) como plan B
- [ ] Capturas de antes y después en las diapositivas
- [ ] Diapositivas en PDF en una USB

**60 minutos antes:**

- [ ] Backend y frontend corriendo con el escenario `todo-bien`
- [ ] Pestañas Estado, Historial, Guía y Alertas abiertas en `http://localhost:5173/?demo=1`
- [ ] Celular en la misma red con la app abierta

## Anexo técnico (no se lee en la presentación)

```bash
cd backend
./mvnw spring-boot:run
```

```bash
cd frontend
npm run dev
```

Cambio de escenario (en Windows PowerShell usar `curl.exe`):

```bash
curl -X POST http://localhost:8080/api/demo/escenario -H "Content-Type: application/json" -d '{"nombre":"humedad-baja"}'
```

Volver al estado normal:

```bash
curl -X POST http://localhost:8080/api/demo/escenario -H "Content-Type: application/json" -d '{"nombre":"todo-bien"}'
```
