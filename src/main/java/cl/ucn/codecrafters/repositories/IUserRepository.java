package cl.ucn.codecrafters.repositories;

import cl.ucn.codecrafters.entities.Role;
import cl.ucn.codecrafters.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends IBaseRepository<User, Integer>{

    Optional<User> findByEmail(String email);

    Boolean existsUserByEmail(String email);

    List<User> findByRole(Role role);


}
