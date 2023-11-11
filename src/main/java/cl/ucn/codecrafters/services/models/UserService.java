package cl.ucn.codecrafters.services.models;

import ch.qos.logback.core.net.server.Client;
import cl.ucn.codecrafters.entities.Role;
import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.entities.dto.AdminitstrativeDto;
import cl.ucn.codecrafters.entities.dto.ClientDto;
import cl.ucn.codecrafters.entities.dto.UserDto;
import cl.ucn.codecrafters.exceptions.NoFoundUserException;
import cl.ucn.codecrafters.repositories.IBaseRepository;
import cl.ucn.codecrafters.repositories.IUserRepository;
import cl.ucn.codecrafters.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService<User, Integer> implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    public UserService(IBaseRepository<User, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<ClientDto> findAllClients() {

        List<User> userList = this.userRepository.findByRole(Role.CLIENT);
        List<ClientDto> clientList = new ArrayList<>();

        if (userList.isEmpty()){
            throw new NoFoundUserException("[!] No existen usuarios en el sistema [!]");
        }

        for (User user : userList){

            ClientDto clientDto = new ClientDto();

            clientDto.setDni(user.getDni());
            clientDto.setFirstName(user.getFirstName());
            clientDto.setLastName(user.getLastName());
            clientDto.setNationality(user.getNationality());
            clientDto.setPhone(user.getPhone());

            clientList.add(clientDto);

        }

        return clientList;
    }

    @Override
    public List<AdminitstrativeDto> findAllAdministratives() {
        return null;
    }

    @Override
    public <E extends UserDto> E findById(Class<E> classType, Integer id) {

        Optional<User> user = this.userRepository.findById(id);

        if (user.isEmpty()){
            throw new NoFoundUserException("Error, no se ha encontrado un usuario con el id: " + id);
        }










        return null;
    }
}
