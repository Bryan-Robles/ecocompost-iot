# EcoCompost IoT

Sistema de monitoreo inteligente para una compostera doméstica en una pensión estudiantil de Santa Marta. Trabajo final del curso **Diseño y Prototipado** (código 4011125), Universidad del Magdalena, Ingeniería de Sistemas, periodo 2026-II. Docente: Sabina Rada Mendoza.

> ⚠️ **Los datos de temperatura y humedad son simulados.** Con autorización de la docente, el trabajo se centra en software e IoT. El nodo físico (ESP32 + sonda DS18B20 + sensor capacitivo de humedad de suelo) es un diseño conceptual. Un *Mock Data Service* reemplaza al hardware y envía lecturas por el mismo contrato de API que usaría el ESP32. Los pesajes de residuos sí son reales y fueron medidos por el equipo.

## Problema

En la casa de Jenis se cocina a diario para más de 12 personas (los dueños y 10 estudiantes). El pesaje propio de 5 días dio **19,5 kg de residuos orgánicos (3,9 kg/día en promedio)**. Hoy esos residuos van en bolsas que escurren y que Cande, encargada del aseo, debe retirar. El esposo de Jenis compra abono para su terraza.

**Pregunta reto (HMW):** ¿Cómo podríamos diseñar una compostera tecnificada e instrumentada con sensores IoT para una pensión estudiantil en Santa Marta, que convierta los altos volúmenes de desechos de la cocina de Jenis en abono para la terraza de su esposo, automatizando el monitoreo para que Cande no tenga trabajo extra de limpieza?

**Decisión:** compostera aeróbica tecnificada con monitoreo IoT. Se descartó el biodigestor por seguridad (metano, presión y olores).

## Arquitectura

```mermaid
flowchart LR
    sensor["ESP32 + sensores<br/>(diseño conceptual)"]
    mock["Mock Data Service<br/>(escenarios en /mock-data)"]
    subgraph backend["Backend · Spring Boot"]
        api["API REST /api"]
        diag["Motor de diagnóstico<br/>(umbrales configurables)"]
        db[("H2 · archivo")]
    end
    ui["Dashboard React<br/>(escritorio y móvil)"]

    sensor -. "HTTP JSON" .-> api
    mock -- "mismo contrato" --> api
    api --> diag
    api --> db
    ui -- "polling cada 5 s" --> api
```

El análisis completo (requisitos, C4, modelo de datos, ADR, backlog y plan) está en [docs/analisis-arquitectura-entregable-3.md](docs/analisis-arquitectura-entregable-3.md).

## Estructura del repositorio

| Carpeta | Contenido |
|---|---|
| `backend/` | API REST en Spring Boot 4 (Java 21, Maven Wrapper, JPA, H2) |
| `frontend/` | Dashboard en React + Vite + TypeScript |
| `mock-data/` | Escenarios JSON de la demo (los usa el simulador del backend y el modo offline del frontend) |
| `docs/` | Arquitectura, decisiones (`adr/`), bitácoras (`bitacoras/`) y evidencias (`evidencias/`) |

## Cómo ejecutar

Requisitos: **Java 21** y **Node.js 24** (ver `frontend/.nvmrc`). No hace falta instalar Maven ni una base de datos.

**Backend** (puerto 8080):

```bash
cd backend
./mvnw spring-boot:run
```

En Windows (cmd o PowerShell) se usa `mvnw.cmd spring-boot:run`. Comprobación rápida: <http://localhost:8080/actuator/health>.

**Frontend** (puerto 5173; las llamadas a `/api` se redirigen al backend):

```bash
cd frontend
npm install
npm run dev
```

**Pruebas y build:**

```bash
cd backend && ./mvnw verify
cd frontend && npm run build
```

## Endpoints

Contrato previsto para el Entregable 3 (detalle en la sección 2.5 del análisis de arquitectura). Estado: en construcción.

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/api/lecturas` | Registrar una lectura (contrato del ESP32 y del simulador) |
| `GET` | `/api/lecturas/actual` | Última lectura y estado de conexión del sensor |
| `GET` | `/api/lecturas?desde=&hasta=` | Historial por rango (ISO-8601) |
| `GET` | `/api/diagnostico` | Estado de temperatura y humedad, fase del compostaje y acciones |
| `GET` | `/api/alertas` | Alertas abiertas y atendidas |
| `PATCH` | `/api/alertas/{id}/atender` | Marcar una alerta como atendida |
| `GET`/`POST` | `/api/cargas` | Cargas de residuos (pesaje real) |
| `GET` | `/api/umbrales` | Umbrales vigentes con su fuente |
| `POST` | `/api/demo/escenario` | Cambiar el escenario del simulador (solo perfil `mock`) |

Ejemplo de lectura, tal como la enviaría el ESP32:

```bash
curl -X POST http://localhost:8080/api/lecturas \
  -H "Content-Type: application/json" \
  -d '{"dispositivoId":"esp32-01","medidoEn":"2026-10-28T14:05:00Z","temperaturaC":58.2,"humedadPct":55.0,"origen":"MANUAL"}'
```

## Umbrales y fuentes

Los rangos de diagnóstico (temperatura normal 45–65 °C; humedad normal 50–60 %) son **una propuesta del equipo pendiente de validar con literatura**. Están en configuración (`application.yml`), no en el código, y el dashboard indica cuáles aún no tienen fuente citada.

## Equipo

| Integrante | Rol de coordinación |
|---|---|
| Bryan Andre Robles Mojica | Líder de usuarios *(por confirmar)* |
| Karyme Judith Sánchez Fernández | Líder de documentación *(por confirmar)* |
| Jussi Torres González | Líder técnico *(por confirmar)* |

Los roles coordinan; no reparten el trabajo técnico. Todos participan en backend, frontend y documentación. Ver [CONTRIBUTING.md](CONTRIBUTING.md).

## Licencia

[MIT](LICENSE)
