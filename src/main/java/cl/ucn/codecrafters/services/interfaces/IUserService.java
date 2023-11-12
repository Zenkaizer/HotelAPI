package cl.ucn.codecrafters.services.interfaces;

import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.entities.dto.AdministrativeDto;
import cl.ucn.codecrafters.entities.dto.ClientDto;
import cl.ucn.codecrafters.entities.dto.UserDto;
import cl.ucn.codecrafters.entities.errors.UserError;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends IBaseService<User, Integer> {

    List<ClientDto> findAllClients();

    List<AdministrativeDto> findAllAdministratives();

    <E extends UserDto> E findUserById(Integer id);

    UserError validateUserErrors(User user);


}
