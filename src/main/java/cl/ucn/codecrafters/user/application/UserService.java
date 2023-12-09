package cl.ucn.codecrafters.user.application;

import cl.ucn.codecrafters.user.domain.UserError;
import cl.ucn.codecrafters.user.domain.dtos.AdministrativeDto;
import cl.ucn.codecrafters.user.domain.dtos.ClientDto;
import cl.ucn.codecrafters.user.domain.dtos.UserDto;
import cl.ucn.codecrafters.user.domain.entities.Role;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.repositories.IUserRepository;
import cl.ucn.codecrafters.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Method responsible for listing all entities.
     *
     * @return All corresponding entities in a list.
     * @throws Exception Exception.
     */
    @Override
    public List<User> findAllUsers() throws Exception {
        try {
            List<User> userList = this.userRepository.findAll();

            if (userList.isEmpty()){
                //TODO: Cambiar
                System.out.println("[!] No existen usuarios en el sistema [!]");
            }

            return userList;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ClientDto> findAllClients() {

        List<User> userList = this.userRepository.findByRole(Role.CLIENT);
        List<ClientDto> clientList = new ArrayList<>();

        if (userList.isEmpty()){
            //TODO: Cambiar
            System.out.println("[!] No existen usuarios en el sistema [!]");
        }

        for (User user : userList){

            ClientDto clientDto = new ClientDto();

            clientDto.setId(user.getId());
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
            //TODO: Cambiar
            System.out.println("[!] No existen usuarios en el sistema [!]");
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

    /**
     * Method in charge of finding an entity according to its ID.
     *
     * @param integer Entity ID.
     * @return An entity according to its ID.
     * @throws Exception Exception.
     */
    @Override
    public User findUserById(Integer integer) throws Exception {
        try {
            Optional<User> entity = this.userRepository.findById(integer);
            return entity.get();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public <E extends UserDto> E findUserDtoById(Integer id) {
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
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || !Validation.
                        validatePattern("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Z|a-z]{2,}$", user.getEmail())) {

            userErrors.setEmailError("El correo electrónico es inválido.");
            isValid = Boolean.FALSE;

        }
        if (!Validation.validatePattern("^(?=.*[0-9])(?=.*[A-Z]).{8,}$", user.getPassword())) {
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

    /**
     * Method responsible for storing an entity in the database.
     *
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    @Override
    public User saveClient(User entity) throws Exception {
        try {

            entity = this.userRepository.save(entity);
            return entity;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for storing an entity in the database.
     *
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    @Override
    public User saveAdministrative(User entity) throws Exception {
        try {

            // Password encode and role assignation.
            entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
            entity.setRole(Role.ADMINISTRATIVE);

            entity = this.userRepository.save(entity);
            return entity;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for storing an entity in the database.
     *
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    @Override
    public User saveAdmin(User entity) throws Exception {
        try {

            // Password encode and role assignation.
            entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
            entity.setRole(Role.ADMIN);

            entity = this.userRepository.save(entity);
            return entity;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Boolean userEmailExists(String email) {
        return this.userRepository.existsUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String clientEmail) {

        Optional<User> userOptional =  this.userRepository.findByEmail(clientEmail);

        return userOptional.orElse(null);
    }

    /**
     * Method responsible for updating an entity in the database.
     *
     * @param integer Entity ID.
     * @param entity  Entity.
     * @return The updated entity.
     * @throws Exception Exception.
     */
    @Override
    public User update(Integer integer, User entity) throws Exception {
        try {
            Optional<User> entityOptional = this.userRepository.findById(integer);
            User entityUpdate = entityOptional.get();
            entityUpdate = this.userRepository.save(entity);
            return entityUpdate;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for removing an entity from the database.
     *
     * @param integer Entity ID.
     * @return True if is deleted or False if not.
     * @throws Exception Exception.
     */
    @Override
    public boolean delete(Integer integer) throws Exception {
        try {
            if (this.userRepository.existsById(integer)){
                Optional<User> entityOptional = this.userRepository.findById(integer);
                User entityDeleted = entityOptional.get();

                entityDeleted.setDeleted(Boolean.TRUE);
                this.userRepository.save(entityDeleted);
                return true;
            }
            else {
                throw new Exception("The item hasn't founded");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
