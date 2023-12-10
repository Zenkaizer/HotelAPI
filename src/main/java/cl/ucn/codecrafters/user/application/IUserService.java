package cl.ucn.codecrafters.user.application;

import cl.ucn.codecrafters.user.domain.administrative.CreateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.ReadAdministrativeDto;
import cl.ucn.codecrafters.user.domain.client.ReadClientDto;
import cl.ucn.codecrafters.user.domain.entities.User;

import java.util.List;

public interface IUserService {


    List<User> findAllUsers() throws Exception;

    List<ReadClientDto> findAllClients();

    List<ReadAdministrativeDto> findAllAdministratives() throws Exception;

    User findUserById(Integer integer) throws Exception;

    boolean delete(Integer integer) throws Exception;

    User update(Integer integer, User entity) throws Exception;

    User saveClient(User entity) throws Exception;

    User saveAdministrative(CreateAdministrativeDto entity) throws Exception;

    User saveAdmin(User entity) throws Exception;

    Boolean userEmailExists(String email);

    User findUserByEmail(String clientEmail);
}
