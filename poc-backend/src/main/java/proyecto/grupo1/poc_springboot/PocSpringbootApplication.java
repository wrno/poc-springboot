package proyecto.grupo1.poc_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
	info = @Info(
		title = "SABOR API",
		version = "1.0",
		description = "API REST de SABOR. Permite gestionar usuarios."
	),
	servers = @Server(
		url = "/",
		description = "Servidor local"
	))
@SpringBootApplication
public class PocSpringbootApplication {
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(PocSpringbootApplication.class, args);
	}

	@Bean
	@Profile("prod")
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String allowedOrigins = env.getProperty("CORS_ALLOWED_ORIGINS");
				registry.addMapping("/**").allowedOrigins(allowedOrigins.split(","));
			}
		};
	}
}
