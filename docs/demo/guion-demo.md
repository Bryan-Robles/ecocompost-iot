# Guion de la demo · Entregable 3

**Presentación del prototipo funcional** · Viernes 30 de octubre de 2026 · 15 min de presentación con demo + 5 min de preguntas

Rúbrica (actividad evaluativa N.° 3): Claridad y coherencia 20 % · Originalidad 30 % · Aplicabilidad 30 % · Presentación y orden 20 %.

> **Roles por confirmar.** El guion usa la propuesta del análisis de arquitectura: Bryan Robles (líder de usuarios), Karyme Sánchez (líder de documentación) y Jussi Torres (líder técnico). Si cambian, solo se cambian los nombres; los tiempos quedan iguales (cada integrante habla unos 5 minutos).

## Mensaje central (decirlo igual en la apertura y el cierre)

> "La cocina de Jenis genera 3,9 kg de residuos orgánicos al día. EcoCompost IoT vigila la compostera y le dice a la familia, en palabras sencillas, qué hacer y cuándo, para que el residuo se vuelva abono para la terraza sin trabajo extra para Cande."

## Guion minuto a minuto

| Min | Quién | Bloque | Qué se dice o se muestra | Criterio de la rúbrica |
|---|---|---|---|---|
| 0:00–2:00 | Bryan | **Problema y usuarios** | Pensión con 12 personas. Pesaje propio del 17 al 22 de octubre de 2026: 19,5 kg en 5 días (3,9 kg/día). Dolores del mapa de empatía: bolsas que escurren (Cande), olores y plagas (Jenis), el esposo compra abono. Punto de vista y pregunta «¿Cómo podríamos…?». | Claridad y coherencia |
| 2:00–4:00 | Karyme | **Ideación y decisión** | Crazy 8s (8 ideas) y matriz impacto/esfuerzo. Compostera aeróbica en lugar de biodigestor: sin metano ni presión, cumple la regla de baja tensión. Alcance: software e IoT con datos simulados; el nodo ESP32 + DS18B20 + sensor capacitivo es diseño conceptual. | Originalidad |
| 4:00–5:30 | Jussi | **Arquitectura y diseño físico** | Diagrama: sensor (simulado) → API Spring Boot → base de datos → dashboard React. Cambio de diseño respaldado en la FAO: el tanque de 50 L se llenaba en unos 5 días; ahora es un bidón de 220 L (unos 22 días de llenado). | Originalidad, Aplicabilidad |
| 5:30–7:30 | Jussi | **Demo 1 · Estado y lectura del sensor** | Pantalla Estado en "todo bien". En la terminal, enviar una lectura con `curl` como lo haría el ESP32 y verla aparecer. Señalar el aviso "Datos simulados". | Aplicabilidad (mide y muestra) |
| 7:30–9:30 | Bryan | **Demo 2 · Alerta y qué hacer** | Activar el escenario `humedad-baja`. La pantalla pasa de verde a ámbar y a rojo. Abrir **Alertas**: "Qué significa / Qué hacer" en lenguaje sencillo y la prueba del puño. Pulsar **Marcar como atendida**. | Aplicabilidad, Originalidad |
| 9:30–11:00 | Karyme | **Demo 3 · Historial y fases** | Pantalla Historial con la banda del rango ideal. Escenario `ciclo-completo`: la fase avanza de mesófila a termófila, enfriamiento y maduración. Escenario `temperatura-alta`. | Aplicabilidad |
| 11:00–12:00 | Jussi | **Demo 4 · Sin conexión y celular** | Escenario `sensor-sin-conexion`: tarjetas en gris y "última lectura hace X min"; al volver, el historial se completa (riesgo R4). Mostrar la vista en el celular. | Aplicabilidad (seguro y robusto) |
| 12:00–13:30 | Karyme | **Seguridad, guía y evidencia** | Lista de riesgos R1–R5 aprobada por la docente. Pantalla **Guía**: quién hace qué (Cande nada extra), guantes y tapabocas, recipiente verde (Resolución 2184 de 2019). Umbrales con fuente: FAO (2013), Tablas 2 y 3. | Aplicabilidad, Claridad |
| 13:30–15:00 | Bryan | **Impacto y cierre** | ≈117 kg/mes que no van al camión (proyección del pesaje). Abono estimado de 35 a 70 kg/mes (rendimiento de 30 a 60 %, CRA 2025). Siguiente paso: prueba con usuarios en la Entrega 4. Repetir el mensaje central. | Claridad y coherencia |
| 15:00–20:00 | Todos | **Preguntas** | Cada integrante responde las preguntas que preparó (ver abajo). | Presentación y orden |

## Qué debe estar listo para cada bloque de la demo

| Bloque | Necesita | Issue | Plan B si no está listo |
|---|---|---|---|
| Demo 1 | Pantalla Estado, `POST /api/lecturas`, simulador | M14, M06, M12 | Estado con fixtures; mostrar el `curl` y el JSON de respuesta en la terminal |
| Demo 2 | Escenarios y cambio en vivo, pantalla Alertas | M13, M16 (hecha) | Alertas con fixtures (ya funciona) |
| Demo 3 | Pantalla Historial y detección de fase | M15, M09 | Historial con `lecturas-7-dias.json` |
| Demo 4 | Estado "sin conexión" y vista móvil | M14, M18 | Fixture `lectura-actual-sin-conexion.json` |
| Seguridad y guía | Pantalla Guía, aprobación de riesgos | M17 (hecha), M22 | Diapositiva con la lista de riesgos |

**Corte:** si el miércoles 28 a las 18:00 (congelamiento) un bloque no está listo, se presenta con su plan B y se dice en voz alta.

## Comandos de la demo

Arranque (jueves en la noche y viernes 60 minutos antes):

```bash
cd backend
./mvnw spring-boot:run
```

```bash
cd frontend
npm run dev
```

Lectura "del ESP32" para la Demo 1 (dejarla escrita en la terminal antes de empezar):

```bash
curl -X POST http://localhost:8080/api/lecturas -H "Content-Type: application/json" -d '{"dispositivoId":"esp32-01","medidoEn":"2026-10-30T14:05:00Z","temperaturaC":58.2,"humedadPct":55.0,"origen":"MANUAL"}'
```

Cambio de escenario (si el selector de la interfaz no está listo):

```bash
curl -X POST http://localhost:8080/api/demo/escenario -H "Content-Type: application/json" -d '{"nombre":"humedad-baja"}'
```

En PowerShell, usar `curl.exe` en lugar de `curl`.

## Planes de respaldo

| Falla | Qué hacer | Quién lo dice |
|---|---|---|
| El backend no arranca o se cae | Pasar al modo con fixtures (sin backend) y decirlo: "pasamos al modo sin conexión que preparamos para esto" | Quien esté hablando |
| No hay internet | Nada cambia: la demo es local | — |
| El portátil falla | Portátil de respaldo con todo instalado | Jussi |
| Todo falla | Video de respaldo (máximo 3 min) y capturas en las diapositivas | Karyme |
| El proyector no muestra bien el celular | Vista móvil del navegador (DevTools) | Jussi |

## Preparación de preguntas

La guía del curso pide que todos estén preparados para estas preguntas. Cada integrante prepara dos.

| Pregunta | Responde | Respuesta corta con evidencia |
|---|---|---|
| ¿Quiénes son sus usuarios y qué descubrieron que no sabían? | Bryan | Jenis, Cande y el esposo (más 2 estudiantes). Lo que no sabíamos: el dolor más fuerte es el trabajo físico de Cande con bolsas que escurren, no solo el olor. |
| ¿Por qué eligieron compostera o biodigestor, y qué descartaron? | Karyme | Compostera aeróbica. Se descartó el biodigestor por metano, presión y olores en una casa con más de 12 personas; la guía exige manejo de gas y presión. |
| ¿Qué valores usaron para decidir si el proceso va bien, y de qué fuentes salen? | Karyme | Fase termófila sobre 45 °C, hasta 65 °C sin intervención, problema sobre 70 °C o bajo 35 °C; humedad ideal 45–60 %. FAO (2013), Tablas 2 y 3. Los márgenes de aviso son criterio del equipo y así se declaran. |
| ¿Qué cambió entre su primer prototipo y el final, y por qué? | Jussi | El tanque pasó de 50 L a 220 L por el cálculo de la FAO; los umbrales se ajustaron a la fuente (por ejemplo, humedad de 50–60 % a 45–60 %); las alertas pasaron de dos a tres niveles. |
| ¿Cuántos residuos se dejarían de enviar a la recolección y cómo lo calcularon? | Bryan | ≈117 kg/mes: 19,5 kg pesados en 5 días ÷ 5 × 30. Es una proyección lineal de 5 días de datos propios. |
| ¿Quién operará el sistema después del curso? | Jussi | El esposo de Jenis atiende las alertas y la bandeja; Jenis deposita los residuos; Cande no hace nada extra. Soporte técnico: Bryan Robles. |

**Preguntas difíciles probables:**

- *"¿Funciona con residuos reales?"* Los residuos y su peso son reales y medidos por el equipo; la telemetría es simulada porque el hardware quedó como diseño conceptual con autorización de la docente. El sistema recibe lecturas por el mismo contrato que usaría el ESP32.
- *"¿Por qué 14 días para el enfriamiento prematuro?"* Es criterio del equipo a partir de la Tabla 7 de la FAO (fase termofílica de 2 a 5 semanas); la FAO no da un umbral explícito.
- *"Las fechas del pesaje y el Entregable 1."* El Entregable 1 (16 de octubre) ya reportaba los días 1 a 3, pero el pesaje registrado es del 17 al 22 de octubre. Aclarar el orden de los pesajes antes de la presentación.

## Lista de chequeo del día

**Jueves 29 (ensayo):**

- [ ] Dos ensayos completos cronometrados (≤ 15 min)
- [ ] Probar cada plan B al menos una vez
- [ ] Grabar el video de respaldo (M27)
- [ ] Portátil de respaldo con backend y frontend instalados
- [ ] Diapositivas exportadas en PDF en una USB

**Viernes 30 (60 minutos antes):**

- [ ] Backend arrancado con el escenario `todo-bien`
- [ ] Frontend abierto en las pestañas Estado, Historial, Alertas y Guía
- [ ] Terminal con el `curl` de la Demo 1 escrito
- [ ] Celular conectado a la misma red, con el dashboard abierto
- [ ] Video de respaldo en el escritorio
- [ ] Guantes y tapabocas a la vista (refuerzan el mensaje de seguridad)
