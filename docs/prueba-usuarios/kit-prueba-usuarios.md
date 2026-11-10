# Kit de prueba con usuarios · Entregable 4

**Proyecto:** EcoCompost IoT · **Etapa:** Evaluar e iterar · **Usuarios:** Jenis, Cande y el esposo de Jenis
**Modalidad:** remota, por videollamada o llamada de WhatsApp, porque los usuarios no están en Santa Marta.
**Duración:** 10 a 15 minutos por persona.

---

## 1. Tarjeta de prueba

Cada hipótesis tiene un criterio claro para decidir si se cumple. Dos de ellas (H3 y H5) salen de la retroalimentación de la docente en la presentación del 30 de octubre.

| # | Creemos que… | Para comprobarlo… | Medimos… | Se cumple si… |
|---|---|---|---|---|
| H1 | Una persona mayor entiende, solo mirando la pantalla de inicio, si la compostera está bien o necesita algo | Mostramos la pantalla Estado en "Todo bien" y luego en alerta, y preguntamos "¿la compostera está bien?" | Respuesta correcta y tiempo hasta responder | Al menos 2 de 3 responden bien, sin ayuda, en menos de 30 segundos |
| H2 | Ante una alerta, el usuario sabe qué hacer | Mostramos una alerta de humedad baja y preguntamos "¿qué haría usted?" | Cuántos pasos de "Qué hacer" logra explicar | El esposo explica al menos 2 de los 3 pasos sin ayuda |
| H3 | El usuario sabe qué hacer con el líquido de la bandeja (lixiviado) | Preguntamos "¿qué haría con el líquido que sale abajo?" | Respuesta | Al menos 2 de 3 dicen algo seguro (no regarlo directo a las plantas, usar guantes) |
| H4 | Cande siente que la app no le da trabajo extra | Le mostramos "¿Quién hace qué?" en la Guía | Su opinión | Cande dice que no le agrega trabajo |
| H5 | El usuario entiende qué significan los números de temperatura y humedad | Preguntamos "¿qué quiere decir humedad 56 %?" | Respuesta | Al menos 2 de 3 explican si está bien o mal, con sus palabras |
| H6 | El usuario sabe si el sistema está funcionando | Mostramos la pantalla con "Sin conexión" y preguntamos "¿el aparato está funcionando?" | Respuesta | Al menos 2 de 3 notan que no está funcionando |

---

## 2. Antes de la llamada

**Roles del equipo:**
- **Bryan (líder de usuarios):** conduce la conversación y hace las preguntas.
- **Karyme (líder de documentación):** anota en la cuadrícula y en la tabla de tareas, y toma el tiempo.
- **Jussi (líder técnico):** maneja la app en el computador y comparte pantalla.

Si alguien no puede, una persona puede conducir y anotar, pero es mejor que anote otra.

**Preparar:**
1. Backend y frontend corriendo:
   ```bash
   cd backend && ./mvnw spring-boot:run
   ```
   ```bash
   cd frontend && npm run dev
   ```
2. Abrir `http://localhost:5173/?demo=1` en el computador.
3. Tener las capturas de `docs/prueba-usuarios/capturas/` (01 a 07) listas en el celular, por si la pantalla compartida no se ve bien. Se pueden mandar por WhatsApp justo antes de cada tarea.
4. Avisar a la persona que la llamada dura unos 10 minutos y que puede tener los lentes a mano.
5. Imprimir o tener abierta una cuadrícula y una tabla de tareas por persona (secciones 5 y 6).

**Cómo compartir pantalla:** en WhatsApp de escritorio, durante la videollamada, el botón "Compartir pantalla". Si la persona no puede ver la pantalla compartida, se le manda la captura por WhatsApp y se le pregunta sobre esa imagen.

**Reglas para quien conduce:**
- No explicar la pantalla antes de preguntar. La idea es ver si la entienden solos.
- No corregir. Si se equivoca, anotar y seguir.
- Preguntas abiertas: "¿Qué ve aquí?", "¿Qué haría?", "¿Qué cree que significa?". Evitar "¿Verdad que es fácil?".
- Si la persona se queda en silencio más de 30 segundos, decir: "No hay respuesta mala, cuénteme qué está pensando".
- Si necesita ayuda, darla, pero anotar "con ayuda".

---

## 3. Guion de la llamada

### 3.1 Inicio (1 minuto)

> "Hola, [nombre]. Le estamos llamando por el proyecto de la compostera. Hicimos una aplicación para ver desde el celular cómo va la compostera. Queremos ver si se entiende bien. No le estamos evaluando a usted, estamos evaluando la aplicación: si algo no se entiende, es culpa de nosotros. ¿Le parece bien si tomamos nota de lo que nos diga? Sus respuestas solo se usan para el trabajo de la universidad."

Anotar en la tabla si dio su permiso (sí/no). Si no lo da, no se hace la prueba.

> "Los datos que va a ver no son de una compostera real: son de prueba, porque el aparato todavía no está construido."

### 3.2 Tareas

Leer cada tarea tal cual. Anotar en la tabla de la sección 5: si lo logró (sí / con ayuda / no), el tiempo aproximado y lo que dijo con sus propias palabras.

**Tareas para las tres personas:**

| Tarea | Pantalla o captura | Qué se dice |
|---|---|---|
| T1 | Estado, "Todo bien" (`01-estado-todo-bien.png`) | "Mire esta pantalla. ¿La compostera está bien o hay que hacer algo?" |
| T2 | Misma pantalla | "¿Qué quiere decir esto de la humedad 56 %? ¿Eso está bien o mal?" |
| T3 | Estado con alerta (`02-estado-alerta.png`) | "Ahora mire esta. ¿Qué está pasando? ¿Qué haría usted?" |
| T4 | Estado sin conexión (`03-estado-sin-conexion.png`) | "¿El aparato de la compostera está funcionando?" |

**Tareas solo para el esposo de Jenis** (es quien atiende las alertas y la bandeja):

| Tarea | Pantalla o captura | Qué se dice |
|---|---|---|
| T5 | Alertas (`04-alertas-detalle.png`) | "Aquí está la alerta. ¿Qué tiene que hacer? ¿Cómo le avisa a la aplicación que ya lo hizo?" |
| T6 | Guía, sección de la bandeja (`07-guia-bandeja.png`) | "Abajo de la compostera se junta un líquido. ¿Qué haría con ese líquido?" |
| T7 | Guía, sección del abono (parte de abajo de `07-guia-bandeja.png`) | "¿Cómo sabe cuándo el abono está listo para su terraza?" |

**Tareas solo para Jenis** (es quien echa los residuos):

| Tarea | Pantalla o captura | Qué se dice |
|---|---|---|
| T8 | Guía, qué se puede echar (`06-guia-residuos.png`) | "¿Puede echar cáscaras de huevo? ¿Y restos de carne?" |
| T6 | Guía, sección de la bandeja (`07-guia-bandeja.png`) | "Si ve líquido abajo de la compostera, ¿qué haría?" |

**Tareas solo para Cande:**

| Tarea | Pantalla o captura | Qué se dice |
|---|---|---|
| T9 | Guía, "¿Quién hace qué?" (`05-guia-quien-hace-que.png`) | "¿Qué le toca hacer a usted con la compostera?" |
| T6 | Guía, sección de la bandeja (`07-guia-bandeja.png`) | "Si ve que la compostera bota líquido al piso, ¿qué haría?" |

### 3.3 Preguntas de cierre (3 minutos)

Hacerlas en este orden:
1. "¿Qué fue lo más fácil de entender?"
2. "¿Qué fue lo más difícil o lo que no entendió?"
3. "¿Pudo leer bien las letras?"
4. "Si tuviera esta aplicación en su celular, ¿la miraría? ¿Cada cuánto?"
5. "¿Qué le cambiaría o le agregaría?"
6. Solo a Cande: "¿Cree que esto le daría más trabajo a usted?"

### 3.4 Despedida

> "Muchas gracias. Con lo que nos dijo vamos a mejorar la aplicación."

---

## 4. Qué observar y anotar (además de las respuestas)

- Dónde se queda mirando o duda.
- Palabras que no entiende o que repite con duda (por ejemplo, "humedad", "lixiviados", "termófila", "fase").
- Si se acerca al celular o pide que se agrande la letra.
- Si confunde colores (verde, ámbar, rojo, gris).
- Frases textuales importantes, entre comillas.

---

## 5. Tabla de tareas (una por persona)

**Persona:** __________ · **Fecha y hora:** __________ · **Medio:** videollamada / llamada + capturas · **Permiso para anotar:** sí / no
**Condujo:** __________ · **Anotó:** __________

| Tarea | Resultado (sí / con ayuda / no) | Tiempo aprox. | Lo que dijo (textual) | Observaciones |
|---|---|---|---|---|
| T1 ¿Está bien? | | | | |
| T2 Humedad 56 % | | | | |
| T3 Alerta | | | | |
| T4 ¿Funciona el aparato? | | | | |
| T5 Atender alerta (esposo) | | | | |
| T6 Líquido de la bandeja | | | | |
| T7 Abono listo (esposo) | | | | |
| T8 Qué se puede echar (Jenis) | | | | |
| T9 Qué le toca (Cande) | | | | |

**Preguntas de cierre:**

| Pregunta | Respuesta (textual) |
|---|---|
| Lo más fácil | |
| Lo más difícil | |
| ¿Pudo leer las letras? | |
| ¿La usaría? ¿Cada cuánto? | |
| ¿Qué cambiaría? | |
| ¿Le da trabajo extra? (Cande) | |

---

## 6. Cuadrícula de retroalimentación (una por persona)

| Le gustó | Se puede mejorar |
|---|---|
| | |

| Preguntas que hizo | Ideas que dio |
|---|---|
| | |

---

## 7. Qué hacer con los resultados

Después de las tres llamadas, enviar al chat del proyecto, por cada persona:
1. La tabla de tareas llena (sección 5).
2. La cuadrícula llena (sección 6).
3. Fecha, hora y medio de la llamada.
4. Si dio permiso para anotar.
5. Si es posible, una captura de la videollamada (solo con permiso) para las evidencias.

Con eso se arman:
- La sección de resultados del Entregable 4 (qué hipótesis se cumplieron y cuáles no).
- La lista de cambios prioritarios, cada uno justificado por lo que dijeron los usuarios.
- La bitácora semanal N.° 4.
