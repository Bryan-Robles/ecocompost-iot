# Escenarios de datos para la demo

| Archivo | Tipo de dato | Uso |
|---|---|---|
| `cargas-pesaje-real.json` | **Real** (pesaje del equipo, Entregable 2) | Cargas de residuos e impacto |
| `todo-bien.json` | Simulado | Proceso sano en fase termófila |
| `humedad-baja.json` | Simulado | Alerta de humedad baja |
| `temperatura-alta.json` | Simulado | Alerta de temperatura alta |
| `sensor-sin-conexion.json` | Simulado | Corte de Wi-Fi (R4) y reenvío |
| `ciclo-completo.json` | Simulado | Las 4 fases del compostaje |

Los escenarios simulados tienen `"valoresIlustrativos": true`: las curvas son sintéticas, pero sus picos se alinean con los rangos de FAO (2013) que usa el diagnóstico (fase termófila sobre 45 °C, problema sobre 70 °C, humedad ideal 45–60 %; ver `docs/referencias.md`). La temperatura ambiente inicial (30 °C) no tiene fuente: hay que medirla o citar al IDEAM. El formato está descrito en la sección 2.6 de `docs/analisis-arquitectura-entregable-3.md`.
