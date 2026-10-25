# Cómo contribuir

## Ramas

| Rama | Uso | Reglas |
|---|---|---|
| `main` | Versión estable que se presenta | Protegida: solo entra por Pull Request desde `develop`, con CI en verde. Se etiqueta por entrega (`v0.3.0` = Entregable 3). |
| `develop` | Integración del trabajo diario | Destino de todos los Pull Request de trabajo. |
| `feature/<id>-<nombre>` | Una tarea del backlog | Sale de `develop`. Ejemplo: `feature/M08-reglas-diagnostico`. Vida corta (≤ 1 día). |
| `fix/<id>-<nombre>` | Corrección de errores | Igual que `feature/*`. |

Flujo:

```bash
git switch develop
git pull
git switch -c feature/M08-reglas-diagnostico
# ...trabajo y commits...
git push -u origin feature/M08-reglas-diagnostico
gh pr create --base develop --fill
```

## Mensajes de commit (Conventional Commits)

Formato: `tipo(alcance): descripción en imperativo`

| Tipo | Cuándo |
|---|---|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de error |
| `test` | Pruebas |
| `docs` | Documentación |
| `refactor` | Cambio interno sin cambiar el comportamiento |
| `chore` | Configuración, dependencias, CI |

Alcances sugeridos: `backend`, `frontend`, `mock`, `docs`, `ci`.

Ejemplos:

```text
feat(backend): agregar regla de humedad configurable
test(backend): cubrir límites 40/50/60/65 % de humedad
docs: registrar bitácora semanal 3
```

Si la issue se cierra con el PR, incluir `Closes #<número>` en la descripción.

## Pull Requests

- Un PR por tarea del backlog; pequeño y revisable en menos de 15 minutos.
- Al menos **1 aprobación** de otro integrante antes de fusionar.
- El CI debe estar en verde.
- Llenar la plantilla (qué cambia, cómo probarlo, capturas si toca la UI).

## Reglas del proyecto

- **Nunca** subir credenciales, tokens ni archivos `.env`.
- Los umbrales de diagnóstico van en configuración, no en el código.
- Todo dato numérico nuevo (rangos, rendimientos, comparaciones) debe tener fuente citada o marcarse como supuesto.
- Desde el **miércoles 28 de octubre a las 18:00** (congelamiento) solo se aceptan PR de tipo `fix`.
