package proyecto.grupo1.poc_springboot.controllers;

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
	@GetMapping("/hi")
	public String sayHi() {
		String hello = "Hello, World!";
		return hello;
	}
}
