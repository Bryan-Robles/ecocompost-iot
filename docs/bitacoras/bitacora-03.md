# Bitácora semanal N.° 3

**Periodo:** 24 al 30 de octubre de 2026 · **Etapa:** Prototipar · **Máximo una página**

| Pregunta | Respuesta |
|---|---|
| ¿Qué hicimos y quién lo hizo? | **Bryan Robles:** backlog M04 (#34), M08 (#35), M17 (#36), M21 (#37) y M16 (#38), del 25 de oct, y el prototipo de punta a punta M06, M07 y M10–M14 (PR #40, 27 de oct), más la corrección de eventos de escenario (PR #42).<br>**Karyme Sánchez:** M20 README (PR #43), M09 detector de fase con media móvil e histéresis (PR #44), M15 selector de rango en Historial (PR #45), M02 navegación con React Router (PR #46) y M18 responsive a 375 px con corrección del estado de error (PR #47), del 28 al 29 de oct.<br>**Jussi Torres:** sin PR fusionado en el repositorio esta semana. Pendiente confirmar su aporte. |
| ¿Qué descubrimos que no sabíamos? | (1) La fase del código era una versión simplificada: no tenía media móvil ni histéresis, aunque el análisis las describía. Además, `dias-enfriamiento-a-maduracion` no correspondía a la ventana del análisis. (2) Con el backend apagado, Estado e Historial quedaban en "Cargando…" sin mostrar el error. (3) `/api/umbrales` y `/api/cargas` no existen todavía: responden 404. |
| ¿Qué decisión tomamos y con qué evidencia? | Alineamos la detección de fase con el análisis (sección 4.4), en lugar de cambiar el análisis al código. Evidencia: `./mvnw verify` en verde, pruebas nuevas de `DetectorDeFase` (pico aislado, bajada corta, maduración a 3 días) y CI en verde en los PR #44 a #47. Los pasos del README se comprobaron contra el backend en marcha. |
| ¿Qué haremos la próxima semana? | Entrega 4: registrar datos a diario, prueba con usuarios reales, cuadrícula de retroalimentación y cambios prioritarios. |
