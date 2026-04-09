package proyecto.grupo1.poc_springboot.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Autenticación de usuarios.")
public class AuthController {
	@GetMapping("/info")
	public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal) {
		Map<String, Object> map = principal.getClaims();
		return Collections.unmodifiableMap(map);
	}
}
