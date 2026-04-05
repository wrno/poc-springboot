package proyecto.grupo1.poc_springboot.services.implementation;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import proyecto.grupo1.poc_springboot.datatypes.UserCreateDTO;
import proyecto.grupo1.poc_springboot.datatypes.UserDTO;
import proyecto.grupo1.poc_springboot.datatypes.UserUpdateDTO;
import proyecto.grupo1.poc_springboot.entities.User;
import proyecto.grupo1.poc_springboot.mappers.UserMapper;
import proyecto.grupo1.poc_springboot.repositories.UserRepository;
import proyecto.grupo1.poc_springboot.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDTO createUser(UserCreateDTO user) throws RuntimeException {
		try {
			User newUser = userMapper.UserCreateDTOToUser(user);
			String encodedPassword =  passwordEncoder.encode(newUser.getPassword());
			newUser.setPassword(encodedPassword);

			try {
				newUser = userRepository.save(newUser);
			} catch (Exception e) {
				logger.error("Error al guardar el nuevo usuario en la base de datos: %1", newUser.toString());
				logger.error("Exception: %1", e.getMessage());
				throw new RuntimeException("Error al crear el usuario.");
			}
			
			return userMapper.UserToUserDTO(newUser);
		} catch (Exception e) {
			logger.error("Error al mapear UserCreateDTO a User: %1", user.toString());
			logger.error("Exception: %1", e.getMessage());
			throw new RuntimeException("Error al crear el usuario.");
		}
	}

	@Override
	public UserDTO getUserByNickname(String nickname) throws IllegalArgumentException {
		User user = userRepository.findByNickname(nickname).orElseThrow(() -> {
			return new IllegalArgumentException("Usuario no encontrado.");
		});
		return userMapper.UserToUserDTO(user);
	}

	@Override
	public UserDTO getUserByEmail(String email) throws IllegalArgumentException {
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			return new IllegalArgumentException("Usuario no encontrado.");
		});
		return userMapper.UserToUserDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			logger.info("No se encontraron usuarios en la base de datos.");
		}
		return userMapper.UsersToUserDTOs(users);
	}

	@Override
	@Transactional
	public UserDTO updateUser(String nickname, UserUpdateDTO user) throws IllegalArgumentException, RuntimeException {
		User existingUser = userRepository.findByNickname(nickname).orElseThrow(() -> {
			logger.warn("Usuario con nickname '%1' no encontrado.", nickname);
			return new IllegalArgumentException("Usuario no encontrado.");
		});

		if (user.email() != null) {
			existingUser.setEmail(user.email());
		}

		if (user.name() != null) {
			existingUser.setName(user.name());
		}

		try {
			existingUser = userRepository.save(existingUser);
		} catch (Exception e) {
			logger.error("Error al actualizar el usuario en la base de datos: %1", existingUser.toString());
			logger.error("Exception: %1", e.getMessage());
			throw new RuntimeException("Error al actualizar el usuario.");
		}

		return userMapper.UserToUserDTO(existingUser);
	}

	@Override
	@Transactional
	public void deleteUser(String nickname) throws IllegalArgumentException {
		User existingUser = userRepository.findByNickname(nickname).orElseThrow(() -> {
			logger.warn("Usuario con nickname '%1' no encontrado.", nickname);
			return new IllegalArgumentException("Usuario no encontrado.");
		});

		userRepository.delete(existingUser);
	}
}
