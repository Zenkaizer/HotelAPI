package cl.ucn.codecrafters.user.application;

import cl.ucn.codecrafters.user.domain.UserError;
import cl.ucn.codecrafters.user.domain.dtos.AdministrativeDto;
import cl.ucn.codecrafters.user.domain.dtos.ClientDto;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.dtos.UserDto;

import java.util.List;

public interface IUserService {


    List<User> findAllUsers() throws Exception;

    List<ClientDto> findAllClients();

    List<AdministrativeDto> findAllAdministratives();

    User findUserById(Integer integer) throws Exception;

    <E extends UserDto> E findUserDtoById(Integer id);

    UserError validateUserErrors(User user);

    boolean delete(Integer integer) throws Exception;

    User update(Integer integer, User entity) throws Exception;

    User saveClient(User entity) throws Exception;

    User saveAdministrative(User entity) throws Exception;

    User saveAdmin(User entity) throws Exception;

    Boolean userEmailExists(String email);

    User findUserByEmail(String clientEmail);
}
