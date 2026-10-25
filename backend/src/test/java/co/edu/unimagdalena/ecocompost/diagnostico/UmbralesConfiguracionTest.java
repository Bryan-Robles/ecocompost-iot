package co.edu.unimagdalena.ecocompost.diagnostico;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/** Comprueba que application.yml se enlaza a UmbralesProperties con los valores esperados. */
@SpringBootTest
@ActiveProfiles("test")
class UmbralesConfiguracionTest {

	@Autowired
	UmbralesProperties propiedades;

	@Test
	void cargaLosUmbralesDeLaConfiguracion() {
		assertThat(propiedades).isEqualTo(UmbralesDePrueba.propuesta());
	}
}
