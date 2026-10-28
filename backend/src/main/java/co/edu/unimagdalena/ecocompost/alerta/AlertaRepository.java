package co.edu.unimagdalena.ecocompost.alerta;

import java.util.List;
import java.util.Optional;

import co.edu.unimagdalena.ecocompost.diagnostico.TipoAlerta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

	Optional<Alerta> findFirstByTipoAndAtendidaEnIsNull(TipoAlerta tipo);

	List<Alerta> findAllByOrderByCreadaEnDesc();
}
