package proyecto.grupo1.poc_springboot.controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import proyecto.grupo1.poc_springboot.datatypes.ErrorMessage;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Autenticación de usuarios.")
public class AuthController {
	@Autowired
	private Environment env;

	@GetMapping("/login")
	@Operation(summary = "Iniciar sesión", description = "Redirige al usuario a la página de inicio de sesión de Keycloak.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "302",
			description = "Redirección a la página de inicio de sesión de Keycloak."),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorMessage.class)))
	})
	public void login(HttpServletResponse response) {
		try {
			String issuerUri = env.getProperty("SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI");
			String clientId = env.getProperty("KEYCLOAK_CLIENT_ID");
			String redirectUri = env.getProperty("KEYCLOAK_REDIRECT_URI");

			response.sendRedirect(
				issuerUri
				+ "/protocol/openid-connect/auth?client_id="
				+ clientId
				+ "&response_type=code&scope=openid&redirect_uri="
				+ URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString()));
		} catch (Exception e) {
			throw new RuntimeException("Error al redirigir a la página de inicio de sesión de Keycloak.", e);
		}
	}
}
