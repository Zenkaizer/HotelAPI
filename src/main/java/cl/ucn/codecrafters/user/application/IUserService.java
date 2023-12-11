package cl.ucn.codecrafters.user.application;

import cl.ucn.codecrafters.user.domain.administrative.CreateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.ReadAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.UpdateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.client.CreateClientDto;
import cl.ucn.codecrafters.user.domain.client.ReadClientDto;
import cl.ucn.codecrafters.user.domain.client.UpdateClientDto;
import cl.ucn.codecrafters.user.domain.entities.User;

import java.util.List;

public interface IUserService {

    User findUserByEmail(String clientEmail);

    List<ReadClientDto> findAllClients();

    List<ReadAdministrativeDto> findAllAdministratives() throws Exception;

    ReadClientDto findClientById(Integer id);

    ReadAdministrativeDto findAdministrativeById(Integer id);

    User saveClient(CreateClientDto entity) throws Exception;

    User saveAdministrative(CreateAdministrativeDto entity) throws Exception;

    User saveAdmin(User entity) throws Exception;

    boolean delete(Integer integer) throws Exception;

    User update(Integer integer, User entity) throws Exception;

    ReadClientDto updateClient(Integer id, UpdateClientDto entity) throws Exception;

    ReadAdministrativeDto updateAdministrative(Integer id, UpdateAdministrativeDto entity) throws Exception;

    boolean userEmailExists(String email);


}
