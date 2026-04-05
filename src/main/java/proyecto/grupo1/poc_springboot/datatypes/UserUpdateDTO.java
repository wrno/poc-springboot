package proyecto.grupo1.poc_springboot.datatypes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(
	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "El email debe ser válido")
	String email,

	@NotBlank(message = "El nombre no puede estar vacío")
	String name
) {

}
