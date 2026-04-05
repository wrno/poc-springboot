package proyecto.grupo1.poc_springboot.services;

import java.util.List;

import proyecto.grupo1.poc_springboot.datatypes.UserCreateDTO;
import proyecto.grupo1.poc_springboot.datatypes.UserDTO;
import proyecto.grupo1.poc_springboot.datatypes.UserUpdateDTO;

public interface UserService {
	UserDTO createUser(UserCreateDTO user) throws RuntimeException;
	UserDTO getUserByNickname(String nickname) throws IllegalArgumentException;
	UserDTO getUserByEmail(String email) throws IllegalArgumentException;
	List<UserDTO> getAllUsers();
	UserDTO updateUser(String nickname, UserUpdateDTO user) throws IllegalArgumentException, RuntimeException;
	void deleteUser(String nickname) throws IllegalArgumentException;
}
