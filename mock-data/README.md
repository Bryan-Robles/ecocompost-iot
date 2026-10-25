# Escenarios de datos para la demo

| Archivo | Tipo de dato | Uso |
|---|---|---|
| `cargas-pesaje-real.json` | **Real** (pesaje del equipo, Entregable 2) | Cargas de residuos e impacto |
| `todo-bien.json` | Simulado | Proceso sano en fase termófila |
| `humedad-baja.json` | Simulado | Alerta de humedad baja |
| `temperatura-alta.json` | Simulado | Alerta de temperatura alta |
| `sensor-sin-conexion.json` | Simulado | Corte de Wi-Fi (R4) y reenvío |
| `ciclo-completo.json` | Simulado | Las 4 fases del compostaje |

Los escenarios simulados tienen `"valoresIlustrativos": true`: sus números (temperatura ambiente, pico, duración de fases) **no salen de una fuente** y deben ajustarse cuando el equipo cite literatura. El formato está descrito en la sección 2.6 de `docs/analisis-arquitectura-entregable-3.md`.
