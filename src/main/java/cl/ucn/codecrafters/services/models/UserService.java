package cl.ucn.codecrafters.services.models;

import cl.ucn.codecrafters.entities.Role;
import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.entities.dto.AdministrativeDto;
import cl.ucn.codecrafters.entities.dto.ClientDto;
import cl.ucn.codecrafters.entities.dto.UserDto;
import cl.ucn.codecrafters.entities.errors.UserError;
import cl.ucn.codecrafters.exceptions.NoFoundUserException;
import cl.ucn.codecrafters.repositories.IBaseRepository;
import cl.ucn.codecrafters.repositories.IUserRepository;
import cl.ucn.codecrafters.services.interfaces.IUserService;
import cl.ucn.codecrafters.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public List<AdministrativeDto> findAllAdministratives() {
        List<User> userList = this.userRepository.findByRole(Role.ADMINISTRATIVE);
        List<AdministrativeDto> adminitstrativeList = new ArrayList<>();

        if (userList.isEmpty()){
            throw new NoFoundUserException("[!] No existen usuarios en el sistema [!]");
        }

        for (User user : userList){

            AdministrativeDto administrativeDto = new AdministrativeDto();

            administrativeDto.setDni(user.getDni());
            administrativeDto.setFirstName(user.getFirstName());
            administrativeDto.setLastName(user.getLastName());
            administrativeDto.setNationality(user.getNationality());
            administrativeDto.setPhone(user.getPhone());

            adminitstrativeList.add(administrativeDto);

        }

        return adminitstrativeList;
    }

    @Override
    public <E extends UserDto> E findUserById(Integer id) {

        Optional<User> user = this.userRepository.findById(id);

        if (user.isEmpty()){
            throw new NoFoundUserException("Error, no se ha encontrado un usuario con el id: " + id);
        }

        User userProvided = user.get();

        if (userProvided.getRole().equals(Role.CLIENT)){

            ClientDto clientDto = new ClientDto();

            clientDto.setDni(userProvided.getDni());
            clientDto.setFirstName(userProvided.getFirstName());
            clientDto.setLastName(userProvided.getLastName());
            clientDto.setNationality(userProvided.getNationality());
            clientDto.setPhone(userProvided.getPhone());

            return (E) clientDto;

        }
        if (userProvided.getRole().equals(Role.ADMINISTRATIVE)){

            AdministrativeDto administrativeDto = new AdministrativeDto();

            administrativeDto.setDni(userProvided.getDni());
            administrativeDto.setFirstName(userProvided.getFirstName());
            administrativeDto.setLastName(userProvided.getLastName());
            administrativeDto.setNationality(userProvided.getNationality());
            administrativeDto.setPhone(userProvided.getPhone());

            return (E) administrativeDto;

        }

        return null;
    }

    @Override
    public UserError validateUserErrors(User user) {

        UserError userErrors = new UserError();
        Boolean isValid = Boolean.TRUE;

        if (user.getDni() == null || user.getDni().trim().isEmpty()) {
            userErrors.setDniError("El RUT/DNI no puede ser vacío o nulo.");
            isValid = Boolean.FALSE;

        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            userErrors.setEmailError("El correo electrónico es inválido.");
            isValid = Boolean.FALSE;

        }
        if (!Validation.validatePattern("^(?=.*[A-Z])(?=.*\\\\d).{8,}$", user.getPassword())) {
            userErrors.setPasswordError("La contraseña no cumple con los requisitos mínimos.");
            isValid = Boolean.FALSE;

        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            userErrors.setFirstNameError("El campo no puede ser nulo o vacío.");
            isValid = Boolean.FALSE;

        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            userErrors.setLastNameError("El campo no puede ser nulo o vacío.");
            isValid = Boolean.FALSE;

        }
        if (user.getPhone().length() < 8 || !user.getPhone().matches("\\d+")) {
            userErrors.setPhoneError("El teléfono debe contener al menos 8 dígitos y debe contener solo números.");
            isValid = Boolean.FALSE;

        }
        if (user.getNationality() == null || user.getNationality().trim().isEmpty()) {
            userErrors.setNationalityError("El campo no puede ser nulo o vacío.");
            isValid = Boolean.FALSE;

        }
        if (user.getBirthDate() == null ||user.getBirthDate().after(new Date())) {
            userErrors.setBirthDateError("La fecha de nacimiento no puede ser posterior a la actual.");
            isValid = Boolean.FALSE;

        }

        userErrors.setIsValid(isValid);
        return userErrors;
    }

}
