package proyecto.grupo1.poc_springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.grupo1.poc_springboot.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByNickname(String nickname);
	Optional<User> findByEmail(String email);
}
