package proyecto.grupo1.poc_springboot.datatypes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
	@Size(min = 8, max = 100, message = "El nickname debe tener entre 8 y 100 caracteres")
	String nickname,

	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "El email debe ser válido")
	String email,

	@NotBlank(message = "El nombre no puede estar vacío")
	String name) {

}
