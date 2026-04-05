package proyecto.grupo1.poc_springboot.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import proyecto.grupo1.poc_springboot.datatypes.UserCreateDTO;
import proyecto.grupo1.poc_springboot.datatypes.UserDTO;
import proyecto.grupo1.poc_springboot.entities.User;

@Mapper
public interface UserMapper {
	User UserCreateDTOToUser(UserCreateDTO user);
	UserDTO UserToUserDTO(User user);
	List<UserDTO> UsersToUserDTOs(List<User> users);
}
