// Alertas de la pantalla Alertas (M16). Se conecta a GET /api/alertas y PATCH /api/alertas/{id}/atender
// a través del cliente común; en modo offline usa los fixtures.

export { atenderAlerta, obtenerAlertas } from './cliente'
