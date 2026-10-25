# Fixtures del contrato de API (M04)

Respuestas de ejemplo con la forma exacta de `src/api/tipos.ts`. Permiten construir las pantallas sin el backend. Las lecturas son ilustrativas; `umbrales.json` usa los valores con fuente de `docs/referencias.md`, y `cargas.json` replica el pesaje real del Entregable 2 (sus fechas quedan en `null` hasta registrar las fechas reales del pesaje).

| Archivo | Endpoint |
|---|---|
| `lectura-actual.json` | `GET /api/lecturas/actual` (en línea) |
| `lectura-actual-sin-conexion.json` | `GET /api/lecturas/actual` (sensor sin conexión) |
| `lecturas-7-dias.json` | `GET /api/lecturas?desde=&hasta=` |
| `diagnostico-todo-bien.json` | `GET /api/diagnostico` |
| `diagnostico-humedad-baja.json` | `GET /api/diagnostico` (alerta) |
| `alertas.json` | `GET /api/alertas` |
| `cargas.json` | `GET /api/cargas` |
| `umbrales.json` | `GET /api/umbrales` |
