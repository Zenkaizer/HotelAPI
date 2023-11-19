package cl.ucn.codecrafters.user;

import cl.ucn.codecrafters.user.dto.AdministrativeDto;
import cl.ucn.codecrafters.user.dto.ClientDto;
import cl.ucn.codecrafters.user.dto.UserDto;

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


}
