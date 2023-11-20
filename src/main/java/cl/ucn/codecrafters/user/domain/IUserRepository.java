package cl.ucn.codecrafters.user.domain;

import cl.ucn.codecrafters.user.domain.Role;
import cl.ucn.codecrafters.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsUserByEmail(String email);

    List<User> findByRole(Role role);


}
