package co.edu.unimagdalena.ecocompost.lectura;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LecturaRepository extends JpaRepository<Lectura, Long> {

	Optional<Lectura> findFirstByOrderByMedidoEnDesc();

	Optional<Lectura> findFirstByDispositivoIdAndMedidoEn(String dispositivoId, Instant medidoEn);

	List<Lectura> findByMedidoEnBetweenOrderByMedidoEnAsc(Instant desde, Instant hasta);

	@Modifying
	@Query("delete from Lectura l where l.origen = :origen")
	void borrarPorOrigen(@Param("origen") OrigenLectura origen);
}
