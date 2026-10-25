# Comandos ejecutados para crear el repositorio

Registro de la Tarea A (25 de octubre de 2026). Se ejecutaron desde la raíz del proyecto en Windows con Git Bash, `git` 2.56 y `gh` 2.103.2 autenticado como `Bryan-Robles`. No se usó ni se subió ninguna credencial.

## 1. Verificación del entorno

```bash
gh auth status            # cuenta Bryan-Robles; permisos repo, workflow, project
gh auth refresh -s project   # lo ejecutó Bryan para poder crear el Project
java -version             # 21.0.11
node -v                   # v24.15.0 (Maven no está instalado: se usa ./mvnw)
```

## 2. Esqueletos

```bash
# Backend generado con Spring Initializr (descargado en una carpeta temporal y copiado a backend/)
curl -sf https://start.spring.io/starter.zip \
  -d type=maven-project -d language=java -d bootVersion=4.1.2.RELEASE -d javaVersion=21 \
  -d groupId=co.edu.unimagdalena -d artifactId=ecocompost-backend -d name=ecocompost-backend \
  -d "description=API REST de EcoCompost IoT" -d packageName=co.edu.unimagdalena.ecocompost \
  -d packaging=jar -d dependencies=web,data-jpa,validation,h2,actuator -o backend.zip
# Ajuste: en pom.xml la versión del padre es 4.1.2 (Maven Central no usa el sufijo .RELEASE)

# Frontend
npm create vite@latest frontend -- --template react-ts --no-interactive
cd frontend && npm install && npm run build

# Verificación del backend
cd backend && ./mvnw -B verify
```

## 3. Repositorio local y primer commit

```bash
git init -b main
git add -A
git update-index --chmod=+x backend/mvnw      # permiso de ejecución para Linux/CI
# Identidad local solo para este repositorio (la global del PC es de otra integrante)
git config user.name "Bryan Robles"
git config user.email "<id>+Bryan-Robles@users.noreply.github.com"
git commit -m "chore: estructura inicial del monorepo EcoCompost IoT"
```

`docs/fuentes/` (PDF de la guía de la docente y de los entregables) está en `.gitignore` y **no** se subió al repositorio público.

## 4. Repositorio en GitHub y ramas

```bash
gh repo create Bryan-Robles/ecocompost-iot --public \
  --description "Monitoreo IoT de una compostera doméstica: dashboard React + API Spring Boot con datos simulados (Unimagdalena, Diseño y Prototipado 2026-II)" \
  --source . --remote origin --push
git switch -c develop
git push -u origin develop
gh repo edit Bryan-Robles/ecocompost-iot --add-topic iot,compostaje,spring-boot,react,unimagdalena --enable-wiki=false
gh repo edit Bryan-Robles/ecocompost-iot --default-branch develop
```

## 5. Etiquetas y milestone

```bash
gh label create backend  --color 1565C0 --description "API Spring Boot" --force
gh label create frontend --color 6A1B9A --description "Dashboard React" --force
gh label create docs     --color 5D4037 --description "Documentación, bitácoras y fuentes" --force
gh label create demo     --color 2E7D32 --description "Preparación de la demo en vivo" --force
gh label create riesgo   --color C62828 --description "Riesgos de seguridad o del proyecto" --force
gh label create must     --color F9A825 --description "MoSCoW: imprescindible para el 30 oct" --force
gh label create should   --color FFE082 --description "MoSCoW: si el Must va a tiempo" --force

gh api repos/Bryan-Robles/ecocompost-iot/milestones \
  -f title="Entrega 3 – 30 oct" -f due_on="2026-10-31T04:59:59Z" \
  -f description="Presentación del prototipo funcional (actividad evaluativa N.° 3)"
```

## 6. Issues (33: M01–M27 y S01–S06)

Se crearon con un script que recorre el backlog de la sección 6 de `analisis-arquitectura-entregable-3.md`. Cada issue se creó así:

```bash
gh issue create -R Bryan-Robles/ecocompost-iot \
  --title "[M08] Umbrales configurables + ReglaHumedad + ReglaTemperatura con pruebas" \
  --body "<tarea, prioridad, horas, dependencias, rol, criterio de aceptación>" \
  --label "backend,must" --milestone "Entrega 3 – 30 oct"
```

Numeración resultante: M01–M27 = #1–#27; S01–S06 = #28–#33.

## 7. Project (tablero Kanban)

```bash
gh project create --owner Bryan-Robles --title "EcoCompost IoT – Entrega 3"     # número 1
gh project link 1 --owner Bryan-Robles --repo Bryan-Robles/ecocompost-iot

# Columnas del campo Status: Backlog, En progreso, Revisión, Hecho
gh api graphql -f query='mutation { updateProjectV2Field(input:{fieldId:"<id campo Status>",
  singleSelectOptions:[{name:"Backlog",color:GRAY,description:"Pendiente"},
  {name:"En progreso",color:YELLOW,description:"Alguien trabaja en ella"},
  {name:"Revisión",color:BLUE,description:"PR abierto esperando aprobación"},
  {name:"Hecho",color:GREEN,description:"Fusionado en develop"}]}) { projectV2Field { __typename } } }'

# Para cada issue 1..33
gh project item-add 1 --owner Bryan-Robles --url https://github.com/Bryan-Robles/ecocompost-iot/issues/<n>
gh project item-edit --project-id <id proyecto> --id <id item> \
  --field-id <id campo Status> --single-select-option-id <id Backlog>
```

Tablero: <https://github.com/users/Bryan-Robles/projects/1>. La vista se cambia a **Board** desde la interfaz web (la API no permite crear vistas).

## 8. Protección de `main`

```bash
gh api -X PUT repos/Bryan-Robles/ecocompost-iot/branches/main/protection --input proteccion.json
```

`proteccion.json`:

```json
{
  "required_status_checks": { "strict": true, "contexts": ["Backend (Maven)", "Frontend (Vite)"] },
  "enforce_admins": false,
  "required_pull_request_reviews": { "required_approving_review_count": 1, "dismiss_stale_reviews": true },
  "restrictions": null,
  "required_conversation_resolution": true,
  "allow_force_pushes": false,
  "allow_deletions": false
}
```

Resultado: a `main` solo se llega por Pull Request con 1 aprobación y CI en verde; no se permite force push ni borrarla. `enforce_admins: false` deja al administrador una salida de emergencia; se puede activar cuando los 3 integrantes sean colaboradores.

## 9. CI

El workflow `.github/workflows/ci.yml` corre en cada PR y push a `main` y `develop`: `./mvnw -B verify` (Java 21 Temurin) y `npm ci && npm run build` (Node según `frontend/.nvmrc`). El primer run quedó en verde en ambas ramas.
