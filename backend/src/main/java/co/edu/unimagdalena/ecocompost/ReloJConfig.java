package co.edu.unimagdalena.ecocompost;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Reloj del sistema como bean, para poder fijarlo en las pruebas. */
@Configuration
public class ReloJConfig {

	@Bean
	Clock reloj() {
		return Clock.systemUTC();
	}
}
