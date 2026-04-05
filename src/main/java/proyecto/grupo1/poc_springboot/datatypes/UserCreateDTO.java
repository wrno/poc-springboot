package proyecto.grupo1.poc_springboot.datatypes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
	@Size(min = 8, max = 100, message = "El nickname debe tener entre 8 y 100 caracteres")
	String nickname,

	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "El email debe ser válido")
	String email,

	@NotBlank(message = "La contraseña no puede estar vacía")
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\ \\!\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\\\\\]\\^\\_\\-`\\{\\|\\}\\~])[A-Za-z\\d\\ \\!\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\\\\\]\\^\\_\\-`\\{\\|\\}\\~]+$",
		message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial")
	String password,

	@NotBlank(message = "El nombre no puede estar vacío")
	String name) {

}
