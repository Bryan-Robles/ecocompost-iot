# Guion de la demo · Entregable 3

**Presentación del prototipo funcional** · Viernes 30 de octubre de 2026 · 15 min de presentación con demo + 5 min de preguntas

Rúbrica (actividad evaluativa N.° 3): Claridad y coherencia 20 % · Originalidad 30 % · Aplicabilidad 30 % · Presentación y orden 20 %.

> **Roles por confirmar.** El guion usa la propuesta del análisis: Bryan Robles (líder de usuarios), Karyme Sánchez (líder de documentación) y Jussi Torres (líder técnico). Si cambian, solo se cambian los nombres.

**Público:** personas sin conocimientos de programación. Se habla de la compostera, de Cande y de la familia; la tecnología se nombra solo si hace falta, en una frase.

## Mensaje central (decirlo al inicio y al cierre)

> "En la cocina de Jenis se generan unos 4 kilos de residuos orgánicos al día. EcoCompost vigila la compostera y le dice a la familia, con palabras sencillas, qué hacer y cuándo, para que esos residuos se conviertan en abono sin trabajo extra para Cande."

## Guion minuto a minuto

| Min | Quién | Bloque | Qué se dice o se muestra | Criterio de la rúbrica |
|---|---|---|---|---|
| 0:00–2:00 | Bryan | **El problema** | La pensión tiene 12 personas. Pesamos los residuos del 17 al 22 de octubre: 19,5 kg en 5 días. Las bolsas escurren, Cande las tiene que limpiar, y el esposo de Jenis compra abono para su terraza. Pregunta: "¿Cómo podríamos…?" | Claridad y coherencia |
| 2:00–4:00 | Karyme | **Cómo llegamos a la idea** | Probamos muchas ideas (8 en una ronda rápida) y elegimos una compostera en lugar de un biodigestor, porque el biodigestor produce gas a presión y malos olores, y no es seguro en casa. Alcance: el software y los datos son reales de prototipo; los datos de temperatura y humedad son simulados, y el sensor físico es un diseño. | Originalidad |
| 4:00–5:30 | Jussi | **Cómo funciona** | Dibujo simple: un sensor mide la temperatura y la humedad → el sistema guarda los datos y decide si todo está bien → la pantalla lo muestra a cualquiera, en el computador o en el celular. Cambio de diseño: el recipiente pasó de 50 litros a 220 litros, según la guía de la FAO. | Originalidad, Aplicabilidad |
| 5:30–7:30 | Jussi | **Demo 1 · Todo está bien** | Mostrar la pantalla **Estado** en verde. Mostrar que llega un dato nuevo y aparece en la pantalla. Señalar el aviso "Datos simulados". | Aplicabilidad |
| 7:30–9:30 | Bryan | **Demo 2 · Una alerta** | Cambiar al caso de humedad baja. La pantalla pasa de verde a ámbar y luego a rojo. Abrir **Alertas**: "Qué significa" y "Qué hacer", en lenguaje simple. Marcarla como atendida. | Aplicabilidad, Originalidad |
| 9:30–11:00 | Karyme | **Demo 3 · La historia de la compostera** | Mostrar **Historial**: la franja verde es el rango ideal. Mostrar cómo cambia la fase del compostaje (calentamiento, enfriamiento, maduración). *Confirmar en el ensayo que el caso de ciclo completo avanza las cuatro fases.* | Aplicabilidad |
| 11:00–12:00 | Jussi | **Demo 4 · Se corta la señal** | Caso de sensor sin conexión: la pantalla se pone en gris y dice cuánto tiempo hace que no llegan datos. Al volver la señal, vuelve a mostrar datos. Mostrar la vista en el celular. | Aplicabilidad (seguro y robusto) |
| 12:00–13:30 | Karyme | **Seguridad y confianza** | Los riesgos de seguridad (electricidad, olores, plagas, manipulación) están en una lista aprobada por la docente. Pantalla **Guía**: qué hace cada persona, con guantes y tapabocas, y la tapa siempre cerrada. Los rangos de temperatura y humedad vienen de la guía de la FAO (2013). | Aplicabilidad, Claridad |
| 13:30–15:00 | Bryan | **Impacto y cierre** | Con este pesaje, unos 117 kg al mes dejarían de ir al camión. El abono estimado es de 35 a 70 kg al mes. Son proyecciones, no mediciones. Siguiente paso: probar el sistema con usuarios reales. Repetir el mensaje central. | Claridad y coherencia |
| 15:00–20:00 | Todos | **Preguntas** | Cada integrante responde las preguntas que preparó (ver abajo). | Presentación y orden |

## Qué debe estar listo para cada bloque

| Bloque | Necesita | Issue | Plan B si no está listo |
|---|---|---|---|
| Demo 1 | Pantalla Estado y llegada de un dato nuevo | M14, M06, M12 | Mostrar la pantalla con datos guardados y decirlo |
| Demo 2 | Cambio de caso y pantalla Alertas | M13, M16 (hecha) | Alertas con datos guardados (ya funciona) |
| Demo 3 | Pantalla Historial y fase | M15, M09 | Historial con datos guardados |
| Demo 4 | Estado "sin conexión" y vista móvil | M14, M18 | Pantalla con datos guardados de "sin conexión" |
| Seguridad y guía | Pantalla Guía y aprobación de riesgos | M17 (hecha), M22 | Diapositiva con la lista de riesgos |

**Corte:** si el miércoles 28 a las 18:00 (congelamiento) un bloque no está listo, se presenta con su plan B y se dice en voz alta.

## Planes de respaldo

| Falla | Qué hacer | Quién lo dice |
|---|---|---|
| El sistema no responde | Pasar a la versión con datos guardados y decirlo: "pasamos al modo sin conexión que preparamos para esto" | Quien esté hablando |
| No hay internet | Nada cambia: la demo es local | — |
| El computador falla | Usar el computador de respaldo, ya preparado | Jussi |
| Todo falla | Video de respaldo (máximo 3 min) y capturas en las diapositivas | Karyme |
| La pantalla del celular no se ve bien en el proyector | Mostrar la vista de celular en el computador | Jussi |

## Preguntas que pueden hacer

| Pregunta | Responde | Respuesta corta |
|---|---|---|
| ¿Quiénes son sus usuarios y qué descubrieron que no sabían? | Bryan | Jenis, Cande, el esposo y los estudiantes. Lo que no sabíamos: el dolor más fuerte es el trabajo físico de Cande con las bolsas que escurren. |
| ¿Por qué compostera y no biodigestor? | Karyme | La compostera es segura en una casa con mucha gente. El biodigestor produce gas y presión, y olores. |
| ¿Cómo saben si el proceso va bien, y de dónde sale eso? | Karyme | Según la FAO (2013): el material está "activo" sobre 45 °C; por encima de 70 °C o por debajo de 35 °C hay un problema; la humedad ideal está entre 45 % y 60 %. Los márgenes de aviso son criterio del equipo y así se dice. |
| ¿Qué cambió desde el primer prototipo? | Jussi | El recipiente pasó de 50 a 220 litros, según el cálculo de la FAO. Los rangos se ajustaron a la fuente. |
| ¿Cuántos residuos dejarían de ir al camión y cómo lo calcularon? | Bryan | Unos 117 kg al mes: los 19,5 kg de 5 días, llevados a un mes. Es una proyección. |
| ¿Quién opera el sistema después del curso? | Jussi | El esposo de Jenis atiende las alertas; Jenis lleva los residuos; Cande no hace trabajo extra. |

**Preguntas difíciles probables:**

- *"¿Los datos son reales?"* Los pesajes de residuos sí son reales. La temperatura y la humedad son simuladas, porque el sensor físico es un diseño. Así lo autorizó la docente.
- *"¿Por qué 14 días?"* Es un criterio del equipo a partir de la guía de la FAO, que da entre 2 y 5 semanas para la fase de calor. La FAO no da un número exacto.
- *"¿Cuándo se pesó?"* Aclarar las fechas de pesaje antes de la presentación: el Entregable 1 reportaba los días 1 a 3, y el pesaje registrado es del 17 al 22 de octubre.

## Lista de chequeo

**Jueves 29 (ensayo):**

- [ ] Dos ensayos completos con cronómetro (máximo 15 min)
- [ ] Probar cada plan B al menos una vez
- [ ] Grabar el video de respaldo (M27)
- [ ] Computador de respaldo listo
- [ ] Diapositivas en PDF en una USB

**Viernes 30 (60 minutos antes):**

- [ ] Sistema encendido con el caso "todo bien"
- [ ] Pantallas Estado, Historial, Alertas y Guía abiertas
- [ ] Celular conectado a la misma red, con la pantalla abierta
- [ ] Video de respaldo en el escritorio
- [ ] Guantes y tapabocas a la vista (refuerzan el mensaje de seguridad)

---

## Anexo para el equipo (técnico, no se lee en la presentación)

Comandos de la Demo 1 y del cambio de caso, para quien opere la demo:

```bash
cd backend
./mvnw spring-boot:run
```

```bash
cd frontend
npm run dev
```

Envío de un dato como lo haría el sensor (en Windows PowerShell usar `curl.exe`):

```bash
curl -X POST http://localhost:8080/api/lecturas -H "Content-Type: application/json" -d '{"dispositivoId":"esp32-01","medidoEn":"2026-10-30T14:05:00Z","temperaturaC":58.2,"humedadPct":55.0,"origen":"MANUAL"}'
```

Cambio de caso (si el selector de la pantalla no está listo):

```bash
curl -X POST http://localhost:8080/api/demo/escenario -H "Content-Type: application/json" -d '{"nombre":"humedad-baja"}'
```
