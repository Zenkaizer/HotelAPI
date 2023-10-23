package cl.ucn.codecrafters.services.models;

import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.repositories.IBaseRepository;
import cl.ucn.codecrafters.repositories.IUserRepository;
import cl.ucn.codecrafters.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, Integer> implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    public UserService(IBaseRepository<User, Integer> baseRepository) {
        super(baseRepository);
    }

}
