# Referencias y trazabilidad de datos

Revisión de fuentes del 25 de octubre de 2026. Cada dato del sistema indica de dónde sale. Las páginas son las **impresas** en el documento (en el PDF de la FAO, la página impresa = página del PDF − 2).

## Referencias (APA 7)

- **[FAO-2013]** Román, P., Martínez, M. M. y Pantoja, A. (2013). *Manual de compostaje del agricultor: Experiencias en América Latina*. Organización de las Naciones Unidas para la Alimentación y la Agricultura, Oficina Regional para América Latina y el Caribe. ISBN 978-92-5-307844-8. https://www.fao.org/4/i3388s/i3388s.pdf
- **[CRA-2025]** Comisión de Regulación de Agua Potable y Saneamiento Básico. (2025, 1 de agosto). *Concepto 20250300086311 de 2025*. https://normas.cra.gov.co/gestor/docs/concepto_cra_0086311_2025.htm
- **[MADS-2019]** Ministerio de Ambiente y Desarrollo Sostenible. (2019). *Resolución 2184 de 2019, por la cual se modifica la Resolución 668 de 2016 sobre uso racional de bolsas plásticas y se adoptan otras disposiciones* (26 de diciembre). Diario Oficial 51.179. https://cancilleria.gov.co/normograma/compilacion/docs/resolucion_minambienteds_2184_2019.htm
- **[SM-2023]** Alcaldía del Distrito de Santa Marta. (2023). *Decreto 461 de 2023, por el cual se modifica el Decreto 070 de 2023 por el cual se adopta la actualización del PGIRS del Distrito Turístico, Cultural e Histórico de Santa Marta* (7 de diciembre). Gaceta Distrital, edición 091. https://www.santamarta.gov.co/sites/default/files/091.pdf
- **[DNP-2022]** Departamento Nacional de Planeación, Ministerio de Ambiente y Desarrollo Sostenible, Ministerio de Vivienda, Ciudad y Territorio, Superintendencia de Servicios Públicos Domiciliarios y Comisión de Regulación de Agua Potable y Saneamiento Básico. (2022). *Guía nacional para la adecuada separación de residuos sólidos*. https://www.minambiente.gov.co/wp-content/uploads/2022/12/Guia_Residuos-Solidos_Digital.pdf
- **[UAESP-2014]** Unidad Administrativa Especial de Servicios Públicos y Universidad Nacional de Colombia, Facultad de Ciencias Agrarias. (2014). *Guía técnica para el aprovechamiento de residuos orgánicos a través de metodologías de compostaje y lombricultura* (Contrato Interadministrativo 369 de 2014). Alcaldía Mayor de Bogotá. https://www.uaesp.gov.co/images/Guia-UAESP_SR.pdf. El año es el del contrato; el documento no imprime fecha de publicación. Se citan páginas del PDF.
- **[IM-2026]** Intendencia de Montevideo. (2026). *Manual de compostaje* [versión web V2]. https://montevideo.gub.uy/sites/default/files/2026-09/2026_manual%20web%20de%20compostaje_V2.pdf. Se citan páginas del PDF.
- **[SALL-2019]** Sall, P. M., Antoun, H., Chalifour, F.-P. y Beauchamp, C. J. (2019). Potential use of leachate from composted fruit and vegetable waste as fertilizer for corn. *Cogent Food & Agriculture, 5*(1), 1580180. https://doi.org/10.1080/23311932.2019.1580180. Solo se leyó el resumen.
- **[HC-2026]** Homecenter Colombia. (s. f.). *Tanque con tapa 220 litros negro Colplast; Regulación moderna hembra macho 1/2 pulgada (Grival); Malla para mosquitero en fibra de vidrio 91,44 cm × 7,62 m (Adfors)*. Recuperado el 9 de noviembre de 2026 de https://www.homecenter.com.co
- **[DYN-2026]** Dynamo Electronics. (s. f.). *Board ESP32-DevKitC-32D; Sensor de temperatura DS18B20 a prueba de agua; Sensor de humedad del suelo; Adaptador 5 V/2 A USB*. Recuperado el 9 de noviembre de 2026 de https://www.dynamoelectronics.com
- **[SIGMA-2026]** Sigma Electrónica. (s. f.). *SEN0193, sensor capacitivo para humedad en suelos (DFRobot)*. Recuperado el 11 de noviembre de 2026 de https://www.sigmaelectronica.net/producto/sen0193/
- **[OCI-2026]** Oracle. (s. f.). *Always Free Resources*. Oracle Cloud Infrastructure Documentation. Recuperado el 11 de noviembre de 2026 de https://docs.oracle.com/en-us/iaas/Content/FreeTier/freetier_topic-Always_Free_Resources.htm
- **[GCP-2026]** Google Cloud. (s. f.). *Free Tier usage limits*. Recuperado el 11 de noviembre de 2026 de https://docs.cloud.google.com/free/docs/free-cloud-features
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

### Manejo del lixiviado (bandeja de abajo)

Revisión del 9 de noviembre de 2026, a partir de la prueba con usuarios del Entregable 4 (hipótesis H3).

| Contenido | Estado | Respaldo |
|---|---|---|
| El lixiviado "contiene también nutrientes solubles y algunos microorganismos" | FAO | [FAO-2013, p. 73] |
| Si el compost está encharcado e inmaduro, el lixiviado puede ser fitotóxico; si viene de compost fresco es oscuro, de pH ácido y con mal olor | FAO | [FAO-2013, p. 73] |
| Vaciar la bandeja con guantes cada semana | FAO | Nota de la Figura 28 [FAO-2013, p. 62] |
| No devolverlo a la compostera en fase de calor: solo se recircula hasta 45 °C, y sobre 55 °C se suspende porque puede llevar patógenos | UAESP | [UAESP-2014, PDF pp. 25–26] |
| Guardarlo en botellas tapadas, aireándolo de vez en cuando, unos 15 días hasta que madure | IM | [IM-2026, PDF p. 13] |
| Diluir 1 litro de lixiviado en 10 litros de agua y regar como máximo cada 15 días | IM | [IM-2026, PDF pp. 12–13] |
| Regar sobre la tierra, no sobre la planta | IM | Preguntas frecuentes del programa Hogares Sustentables, Intendencia de Montevideo |
| Si después del reposo sigue oscuro o con mal olor, no usarlo en plantas | EQUIPO | Aplica las señales de lixiviado de compost fresco [FAO-2013, p. 73] |
| Si sigue oscuro o con mal olor, diluirlo en agua y desecharlo por un sifón conectado al alcantarillado | EQUIPO | Decisión del equipo del 9 de noviembre de 2026; ninguna fuente revisada indica cómo desecharlo |
| Para reducir el lixiviado, agregar material seco cuando la humedad pase de 60 % | FAO | Tabla 2 [FAO-2013, p. 27] |

**Contexto:** el lixiviado de compost de frutas y verduras es ácido y de alta conductividad; dañó el maíz a dosis altas, pero a dosis controladas rindió como el fertilizante mineral [SALL-2019, resumen]. El 1:10 de Montevideo no trae fuente propia. La dosis del lixiviado de **lombricompost** (10 % al suelo, 5 % foliar [UAESP-2014, PDF p. 58]) no aplica a esta compostera, que no usa lombrices.

**Sin fuente:** cómo desechar el lixiviado que no se puede usar. No se encontró guía oficial sobre verterlo al desagüe, así que la regla del sifón es criterio del equipo. Debe ser un sifón del alcantarillado sanitario, no una rejilla de aguas lluvias.

Ya no se usa "agregar 1 litro de agua" (venía del wireframe sin fuente); se reemplazó por la técnica del puño. Tampoco se usa la proporción 3:1 marrón/verde: la FAO expresa la mezcla como relación C:N, no como proporción de volúmenes.

## Impacto y contexto

| Dato | Valor | Respaldo |
|---|---|---|
| Residuos orgánicos de la pensión | 19,5 kg en 5 días (17 y 19 a 22 de octubre de 2026); 3,9 kg/día | Pesaje propio [EQUIPO-E2] |
| Residuos desviados en un ciclo del bidón | ≈ 86 kg | Cálculo propio: 3,9 kg/día × 22 días de llenado del bidón de 220 L |
| Duración de un ciclo | ≈ 9–13 semanas | 22 días de llenado + 6 a 10 semanas de maduración [FAO-2013, p. 61] |
| Potencial de la casa con más bidones | ≈ 117 kg/mes | Proyección lineal propia (3,9 kg/día × 30) |
| Por persona | ≈ 0,33 kg/persona/día de orgánicos crudos | Cálculo propio: 3,9 kg/día ÷ 12 personas (dueños y 10 estudiantes, [EQUIPO-E2]) |
| Rendimiento de compost | 30–60 % del peso inicial; cerca de 50 % típico | [CRA-2025], que recopila a Haug (1993), Diaz et al. (1993) y CDFA (2022). **Fuente secundaria** |
| Abono estimado por ciclo | ≈ 26–51 kg | Cálculo propio: 86 kg × 30–60 % [CRA-2025] |
| Reducción de volumen de la pila | hasta 50 % | [FAO-2013, p. 31] |
| Generación nacional | 515 kg por habitante; 47 % de los 24,8 millones de t/año viene de hogares | DANE citado en [DNP-2022, p. 6 del PDF]. La guía no indica el periodo de los 515 kg ni separa los orgánicos |
| Normativa local | PGIRS de Santa Marta adoptado por Decreto 070 de 2023 y modificado por Decreto 461 de 2023 | [SM-2023] |

**Retirado:** "un hogar estándar genera 1,2 kg/día" y "la pensión genera 3 veces más". No se encontró fuente para 1,2 kg/día, y las cifras oficiales son por persona e incluyen todo tipo de residuos, así que no son comparables con 3,9 kg/día de orgánicos de toda la casa.

**Límite de un solo bidón:** el bidón de 220 L se llena el día 22 y después madura de 6 a 10 semanas sin recibir residuos [FAO-2013, p. 61]; mientras tanto, los residuos vuelven a la recolección. Si el sistema se implementa, hay que aumentar el número de bidones y planificar una estrategia que coordine los días de llenado, la rotación entre bidones y los días del camión recolector (martes, jueves y sábado).

## Costos de la propuesta de implementación (E5)

Precios de una unidad consultados entre el 9 y el 11 de noviembre de 2026. No se verificó si incluyen IVA.

| Componente | Referencia | Precio (COP) | Respaldo |
|---|---|---|---|
| Bidón con tapa | Tanque con tapa 220 L, Colplast | 199.900 | [HC-2026] |
| Microcontrolador | ESP32-DevKitC-32D (agotado el día de la consulta) | 44.000 | [DYN-2026] |
| Sensor de temperatura | DS18B20 a prueba de agua | 15.000 | [DYN-2026] |
| Sensor de humedad | Sensor capacitivo anticorrosivo DFRobot SEN0193 | 30.940 | [SIGMA-2026] |
| Alimentación | Adaptador 5 V / 2 A USB (agotado el día de la consulta) | 26.000 | [DYN-2026] |
| Caja para el ESP32 | Caja hermética 10 × 10, Halux (la tienda no indica el grado IP) | 14.200 | [HC-2026] |
| Resistencia de 4,7 kΩ | Kit de 200 resistencias de 1/4 W (no confirma el valor de 4,7 kΩ) | 10.000 | [DYN-2026] |
| Cables | Cables dupont de 20 cm, 10 unidades | 3.500 | [DYN-2026] |
| Encapsulado de los sensores | Kit de resina epóxica Epoglass 1/4 de galón, Preflex (alcanza para varias unidades) | 114.900 | [HC-2026] |
| Llave de la bandeja | Regulación 1/2", Grival | 36.900 | [HC-2026] |
| Malla | 1 m de un rollo de 7,62 m a 99.900 (cálculo propio) | 13.100 | [HC-2026] |
| Recipiente del líquido | Balde de 10 L, Estra | 23.900 | [HC-2026] |
| Guantes | Guantes de nitrilo, caja de 100, Steelpro | 30.900 | [HC-2026] |
| Tapabocas | Tapabocas desechables, paquete de 50, Element | 16.900 | [HC-2026] |
| Servidor | Máquina virtual Always Free de Oracle Cloud | 0 | [OCI-2026] |
| Software | Spring Boot, React y H2 (código abierto) | 0 | |
| **Total de una unidad** | | **580.140** | |

**Servidor elegido:** máquina virtual *Always Free* de Oracle Cloud. Gratuita "for the life of the account", con hasta el equivalente a 2 OCPU y 12 GB de memoria en Ampere A1 y 200 GB de almacenamiento en bloque, así que el archivo H2 se conserva entre reinicios [OCI-2026]. Riesgo: Oracle puede recuperar una instancia inactiva si durante 7 días su CPU, red y memoria se quedan bajo el 20 % [OCI-2026]. Alternativa: e2-micro del nivel gratuito de Google Cloud, con disco persistente de 30 GB pero con cuenta de facturación obligatoria [GCP-2026]. Se descartó Render gratis porque su sistema de archivos se borra al reiniciar y no permite disco persistente.

**Consumo eléctrico máximo:** 5 V × 2 A = 10 W → 0,24 kWh/día → 7,2 kWh/mes si el adaptador trabajara siempre al máximo (cálculo propio; el ESP32 consume menos).

## Hallazgos de las fuentes que afectan el diseño

1. **Volumen del contenedor.** El ejemplo 8 de la FAO [FAO-2013, p. 64] dimensiona una compostera con una densidad compactada de 400 kg/m³: una familia que produce 20 kg por semana necesita un bidón de 220 L para 5 semanas de llenado. La pensión produce unos 27 kg por semana (3,9 kg/día × 7). Con el mismo método, 2 semanas de llenado ocupan unos 137 L (27,3 kg × 2 ÷ 400 kg/m³ ≈ 0,137 m³), y la FAO sugiere llenar el recipiente durante 2 o 3 semanas [FAO-2013, p. 64]. El tanque de 50 L del diseño conceptual (E2) se llenaría en unos 5 días. **Decisión del equipo (25 de octubre de 2026): usar un bidón de 220 L**, como el del manual. Con 3,9 kg/día y 400 kg/m³ se llena en unos 22 días (220 L × 0,4 kg/L ÷ 3,9 kg/día), dentro de las 2 o 3 semanas de llenado que sugiere la FAO.
2. **Clima cálido.** La FAO advierte que los sistemas cerrados pueden alcanzar temperaturas altas y recomienda agregar tierra en climas cálidos [FAO-2013, p. 60]. Refuerza la alerta de temperatura alta para Santa Marta.

## Criterios del equipo sin fuente externa

| Dato | Uso | Criterio |
|---|---|---|
| Temperatura ambiente de Santa Marta: 30 °C | Curvas del Mock Data Service y detección de maduración | El equipo eligió 30 °C como temperatura promedio de Santa Marta |
| Curvas de los escenarios de demo | `mock-data/*.json` | Son ilustrativas por diseño (`valoresIlustrativos: true`); sus picos y fases se alinearon con los rangos de la FAO |
