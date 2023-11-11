package cl.ucn.codecrafters.services.interfaces;

import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.entities.dto.AdminitstrativeDto;
import cl.ucn.codecrafters.entities.dto.ClientDto;
import cl.ucn.codecrafters.entities.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IBaseService<User, Integer> {

    List<ClientDto> findAllClients();

    List<AdminitstrativeDto> findAllAdministratives();

    <E extends UserDto> E findById(Class<E> classType, Integer id);


}
