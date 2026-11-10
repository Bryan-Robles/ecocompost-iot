# Registro de decisiones de arquitectura (ADR)

Las decisiones del Entregable 3 están resumidas en la sección 3 de [analisis-arquitectura-entregable-3.md](../analisis-arquitectura-entregable-3.md):

| ADR | Decisión | Estado |
|---|---|---|
| ADR-001 | H2 en modo archivo como base de datos del prototipo | Aceptada |
| ADR-002 | Polling cada 5 s en lugar de WebSocket/SSE | Aceptada |
| ADR-003 | Recharts para las gráficas | Aceptada |
| ADR-004 | Estado local de React (sin Redux) | Aceptada con cambio: no se usó TanStack Query; cada pantalla consulta la API con `setInterval` y `useState` |
| ADR-005 | Autenticación fuera de alcance | Aceptada |
| ADR-006 | Demo local con modo offline de respaldo (`VITE_MODO=offline`) | Aceptada |
| ADR-007 | Monorepo backend/frontend/docs/mock-data | Aceptada |

Estados revisados el 9 de noviembre de 2026 contra el código. El análisis del Entregable 3 conserva el texto original de cada ADR.

Para una decisión nueva, crear `NNN-titulo.md` con: contexto, opciones, decisión y consecuencias.
