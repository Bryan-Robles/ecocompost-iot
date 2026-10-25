# Referencias y trazabilidad de datos

Revisión de fuentes del 25 de octubre de 2026. Cada dato del sistema indica de dónde sale. Las páginas son las **impresas** en el documento (en el PDF de la FAO, la página impresa = página del PDF − 2).

## Referencias (APA 7)

- **[FAO-2013]** Román, P., Martínez, M. M. y Pantoja, A. (2013). *Manual de compostaje del agricultor: Experiencias en América Latina*. Organización de las Naciones Unidas para la Alimentación y la Agricultura, Oficina Regional para América Latina y el Caribe. ISBN 978-92-5-307844-8. https://www.fao.org/4/i3388s/i3388s.pdf
- **[CRA-2025]** Comisión de Regulación de Agua Potable y Saneamiento Básico. (2025, 1 de agosto). *Concepto 20250300086311 de 2025*. https://normas.cra.gov.co/gestor/docs/concepto_cra_0086311_2025.htm
- **[MADS-2019]** Ministerio de Ambiente y Desarrollo Sostenible. (2019). *Resolución 2184 de 2019, por la cual se modifica la Resolución 668 de 2016 sobre uso racional de bolsas plásticas y se adoptan otras disposiciones* (26 de diciembre). Diario Oficial 51.179. https://cancilleria.gov.co/normograma/compilacion/docs/resolucion_minambienteds_2184_2019.htm
- **[SM-2023]** Alcaldía del Distrito de Santa Marta. (2023). *Decreto 461 de 2023, por el cual se modifica el Decreto 070 de 2023 por el cual se adopta la actualización del PGIRS del Distrito Turístico, Cultural e Histórico de Santa Marta* (7 de diciembre). Gaceta Distrital, edición 091. https://www.santamarta.gov.co/sites/default/files/091.pdf
- **[DNP-2022]** Departamento Nacional de Planeación, Ministerio de Ambiente y Desarrollo Sostenible, Ministerio de Vivienda, Ciudad y Territorio, Superintendencia de Servicios Públicos Domiciliarios y Comisión de Regulación de Agua Potable y Saneamiento Básico. (2022). *Guía nacional para la adecuada separación de residuos sólidos*. https://www.minambiente.gov.co/wp-content/uploads/2022/12/Guia_Residuos-Solidos_Digital.pdf
- **[EQUIPO-E2]** Robles Mojica, B. A., Sánchez Fernández, K. J. y Torres González, J. (2026). *EcoCompost IoT: Entregable 2. Idear y prototipar en baja fidelidad* [Documento de curso]. Universidad del Magdalena.

## Umbrales de diagnóstico

Los valores viven en `backend/src/main/resources/application.yml`. Estado: **FAO** = tomado de la fuente; **EQUIPO** = criterio del equipo derivado de la fuente, declarado como tal.

| Umbral | Valor | Estado | Respaldo |
|---|---|---|---|
| Inicio de fase termófila / temperatura normal mínima | 45 °C | FAO | "Cuando el material alcanza temperaturas mayores que los 45°C" comienza la fase termófila [FAO-2013, p. 23] |
| Temperatura normal máxima | 65 °C | FAO | El compostaje "puede subir hasta los 65°C" sin calentamiento externo [FAO-2013, p. 28] |
| Temperatura crítica alta | > 70 °C | FAO | Tabla 3, "Altas temperaturas (>70°C)": se inhibe la descomposición [FAO-2013, p. 28] |
| Zona de atención alta | 65–70 °C | EQUIPO | Entre el máximo esperado (p. 28) y el umbral de problema de la Tabla 3 |
| Temperatura crítica baja | < 35 °C | FAO | Tabla 3, "Bajas temperaturas (< 35°C)" [FAO-2013, p. 28] |
| Enfriamiento prematuro | antes de 14 días desde la carga | EQUIPO | Interpretación de la Tabla 7: la fase termofílica ocupa de 2 a 5 semanas [FAO-2013, p. 31]. La FAO no da un umbral explícito |
| Humedad normal | 45–60 % | FAO | "El rango óptimo de humedad para compostaje es del 45% al 60%"; Tabla 2 [FAO-2013, p. 27] |
| Humedad crítica baja | < 40 % | EQUIPO | Bajo 45 % la FAO ya señala problema (Tabla 2); el equipo deja 5 puntos de aviso (ámbar) antes de rojo |
| Humedad crítica alta | > 65 % | EQUIPO | Sobre 60 % la FAO ya señala problema (Tabla 2); mismo margen de aviso |
| Fase mesófila | ambiente → 45 °C, 2 a 8 días | FAO | [FAO-2013, p. 23] |
| Enfriamiento (mesófila II) | baja a 40–45 °C, varias semanas | FAO | [FAO-2013, p. 24] |
| Maduración | temperatura ambiente, meses | FAO | [FAO-2013, p. 24] |

**Cambio frente a la propuesta original del equipo:** humedad normal 50–60 % pasa a 45–60 %; temperatura crítica alta 65 °C pasa a 70 °C; temperatura crítica baja 40 °C pasa a 35 °C; plazo de enfriamiento prematuro 7 días pasa a 14 días.

**Limitación conocida:** la Tabla 7 [FAO-2013, p. 31] da humedad ideal de 30–40 % para compost **maduro**. La regla de humedad no distingue fases todavía, así que en maduración podría avisar "humedad baja" sin que sea un problema.

## Guía de uso y acciones

| Contenido | Respaldo |
|---|---|
| Medir la humedad con la "técnica del puño": el material queda apelmazado sin escurrir | [FAO-2013, p. 56] |
| Humedad baja: agregar agua o material fresco (restos de fruta y verdura) | Tabla 2 [FAO-2013, p. 27] |
| Humedad alta: voltear y agregar material seco rico en carbono (aserrín, paja, hojas secas) | Tabla 2 [FAO-2013, p. 27] |
| Temperatura alta: voltear, verificar humedad (55–60 %) y agregar material de lenta degradación (madera, pasto seco) | Tabla 3 [FAO-2013, p. 28] |
| Temperatura baja: revisar humedad, agregar material o material rico en nitrógeno | Tabla 3 [FAO-2013, p. 28] |
| En clima cálido se puede agregar hasta 10 % de tierra para regular la temperatura | [FAO-2013, p. 60] |
| Grifo de lixiviado: abrirlo cada semana | Nota de la Figura 28 [FAO-2013, p. 62] |
| En compostera cerrada, la maduración llega en 6 a 10 semanas | [FAO-2013, p. 61] |
| El compost está maduro si no se recalienta al voltearlo, es oscuro y huele a suelo húmedo | [FAO-2013, p. 57] |
| Relación C:N inicial de 25:1 a 35:1 | [FAO-2013, p. 64] |
| Bolsa o recipiente **verde** para residuos orgánicos aprovechables | Artículo 4 [MADS-2019] |

Ya no se usa "agregar 1 litro de agua" (venía del wireframe sin fuente); se reemplazó por la técnica del puño. Tampoco se usa la proporción 3:1 marrón/verde: la FAO expresa la mezcla como relación C:N, no como proporción de volúmenes.

## Impacto y contexto

| Dato | Valor | Respaldo |
|---|---|---|
| Residuos orgánicos de la pensión | 19,5 kg en 5 días; 3,9 kg/día | Pesaje propio [EQUIPO-E2] |
| Residuos desviados al mes | ≈ 117 kg/mes | Proyección lineal propia (3,9 kg/día × 30) |
| Por persona | ≈ 0,33 kg/persona/día de orgánicos crudos | Cálculo propio: 3,9 kg/día ÷ 12 personas (dueños y 10 estudiantes, [EQUIPO-E2]) |
| Rendimiento de compost | 30–60 % del peso inicial; cerca de 50 % típico | [CRA-2025], que recopila a Haug (1993), Diaz et al. (1993) y CDFA (2022). **Fuente secundaria** |
| Abono estimado | ≈ 35–70 kg/mes | Cálculo propio: 117 kg × 30–60 % [CRA-2025] |
| Reducción de volumen de la pila | hasta 50 % | [FAO-2013, p. 31] |
| Generación nacional | 515 kg por habitante; 47 % de los 24,8 millones de t/año viene de hogares | DANE citado en [DNP-2022, p. 6 del PDF]. La guía no indica el periodo de los 515 kg ni separa los orgánicos |
| Normativa local | PGIRS de Santa Marta adoptado por Decreto 070 de 2023 y modificado por Decreto 461 de 2023 | [SM-2023] |

**Retirado:** "un hogar estándar genera 1,2 kg/día" y "la pensión genera 3 veces más". No se encontró fuente para 1,2 kg/día, y las cifras oficiales son por persona e incluyen todo tipo de residuos, así que no son comparables con 3,9 kg/día de orgánicos de toda la casa.

## Hallazgos de las fuentes que afectan el diseño

1. **Volumen del contenedor.** El ejemplo 8 de la FAO [FAO-2013, p. 64] dimensiona una compostera con una densidad compactada de 400 kg/m³: una familia que produce 20 kg por semana necesita un bidón de 220 L para 5 semanas de llenado. La pensión produce unos 27 kg por semana (3,9 kg/día × 7). Con el mismo método, 2 semanas de llenado ocupan unos 137 L (27,3 kg × 2 ÷ 400 kg/m³ ≈ 0,137 m³), y la FAO sugiere llenar el recipiente durante 2 o 3 semanas [FAO-2013, p. 64]. **El tanque de 50 L del diseño conceptual (E2) se llenaría en unos 5 días.** Conviene revisar el diseño: por ejemplo, un bidón de 220 L o dos recipientes alternados.
2. **Clima cálido.** La FAO advierte que los sistemas cerrados pueden alcanzar temperaturas altas y recomienda agregar tierra en climas cálidos [FAO-2013, p. 60]. Refuerza la alerta de temperatura alta para Santa Marta.

## Datos que siguen sin fuente

| Dato | Uso | Cómo cerrarlo |
|---|---|---|
| Temperatura ambiente de Santa Marta (30 °C en el simulador) | Curvas del Mock Data Service | No se encontraron las normales climatológicas del IDEAM. Medirla con un termómetro (foto, fecha y hora) o citar las normales del IDEAM para la estación del aeropuerto Simón Bolívar |
| Fechas exactas de los 5 pesajes | `cargas-pesaje-real.json` y fixtures | Notas de campo de Bryan Robles |
| Curvas de los escenarios de demo | `mock-data/*.json` | Son ilustrativas por diseño (`valoresIlustrativos: true`); sus picos y fases se alinearon con los rangos de la FAO |
