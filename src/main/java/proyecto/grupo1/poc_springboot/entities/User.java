package proyecto.grupo1.poc_springboot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
	@Id
	@Size(min = 8, max = 100, message = "El nickname debe tener entre 8 y 100 caracteres")
	private String nickname;

	@Column(unique = true)
	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "El email debe ser válido")
	private String email;

	@NotBlank(message = "La contraseña no puede estar vacía")
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\ \\!\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\\\\\]\\^\\_\\-`\\{\\|\\}\\~])[A-Za-z\\d\\ \\!\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\\\\\]\\^\\_\\-`\\{\\|\\}\\~]+$",
		message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial")
	private String password;

	@NotBlank(message = "El nombre no puede estar vacío")
	private String name;
}
