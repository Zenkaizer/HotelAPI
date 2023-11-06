package cl.ucn.codecrafters.services.models;

import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.entities.dto.UserDto;
import cl.ucn.codecrafters.repositories.IBaseRepository;
import cl.ucn.codecrafters.repositories.IUserRepository;
import cl.ucn.codecrafters.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<User> findAll() throws Exception {
        return null;
    }

    @Override
    public User findById(Integer integer) throws Exception {
        return null;
    }

    @Override
    public User save(User entity) throws Exception {
        return null;
    }

    @Override
    public User update(Integer integer, User entity) throws Exception {
        return null;
    }

    @Override
    public boolean delete(Integer integer) throws Exception {
        return false;
    }

    public List<UserDto> findAllUser(){

        try {
            List<User> entities = null;
            return null;
        }catch (Exception e){
            return null;
        }


    }
}
