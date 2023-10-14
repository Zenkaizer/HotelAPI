package cl.ucn.codecrafters.repositories;

import cl.ucn.codecrafters.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends IBaseRepository<User, Integer>{
}
