package proyecto.grupo1.poc_springboot.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import proyecto.grupo1.poc_springboot.datatypes.UserCreateDTO;
import proyecto.grupo1.poc_springboot.datatypes.UserDTO;
import proyecto.grupo1.poc_springboot.datatypes.UserUpdateDTO;
import proyecto.grupo1.poc_springboot.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios.")
public class UserController {
	private final UserService userService;

	@GetMapping
	@Operation(
		summary = "Listar usuarios",
		description = "Devuelve una lista de todos los usuarios registrados.")
	@ApiResponse(
		responseCode = "200",
		description = "Lista de usuarios obtenida exitosamente.",
		content = @Content(
			mediaType = "application/json",
			schema = @Schema(implementation = UserDTO.class)))
	public List<UserDTO> getUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{nickname}")
	@Operation(summary = "Obtener usuario por nickname", description = "Devuelve los detalles de un usuario específico dado su nickname.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Usuario encontrado exitosamente.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserDTO.class))),
		@ApiResponse(
			responseCode = "400",
			description = "El usuario no existe.",
			content = @Content(mediaType = "text/plain"))
	})
	public UserDTO getUserByNickname(@PathVariable(name = "nickname") String nickname) {
		UserDTO user = userService.getUserByNickname(nickname);
		return user;
	}

	@PostMapping
	@Operation(summary = "Crear usuario", description = "Crea un nuevo usuario con los datos proporcionados.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Usuario creado exitosamente.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserDTO.class))),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(mediaType = "text/plain"))
	})
	public UserDTO createUser(@RequestBody UserCreateDTO user) {
		return userService.createUser(user);
	}

	@PutMapping("/{nickname}")
	@Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente dado su nickname.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Usuario actualizado exitosamente.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserDTO.class))),
		@ApiResponse(
			responseCode = "400",
			description = "El usuario no existe.",
			content = @Content(mediaType = "text/plain")),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(mediaType = "text/plain"))
	})
	public UserDTO updateUser(@PathVariable(name = "nickname") String nickname, @RequestBody UserUpdateDTO user) {
		return userService.updateUser(nickname, user);
	}

	@DeleteMapping("/{nickname}")
	@Operation(summary = "Eliminar usuario", description = "Elimina un usuario existente dado su nickname.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Usuario eliminado exitosamente.",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(
			responseCode = "400",
			description = "El usuario no existe.",
			content = @Content(mediaType = "text/plain")),
		@ApiResponse(
			responseCode = "500",
			description = "Error interno del servidor.",
			content = @Content(mediaType = "text/plain"))
	})
	public void deleteUser(@PathVariable(name = "nickname") String nickname) {
		userService.deleteUser(nickname);
	}
}
