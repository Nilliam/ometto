package dev.ometto;

import dev.ometto.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class OmettoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmettoApplication.class, args);
	}

}
