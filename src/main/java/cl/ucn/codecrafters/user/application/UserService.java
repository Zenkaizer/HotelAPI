package cl.ucn.codecrafters.user.application;

import cl.ucn.codecrafters.user.domain.administrative.CreateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.ReadAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.UpdateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.client.CreateClientDto;
import cl.ucn.codecrafters.user.domain.client.ReadClientDto;
import cl.ucn.codecrafters.user.domain.client.UpdateClientDto;
import cl.ucn.codecrafters.user.domain.entities.Role;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.repositories.IUserRepository;
import cl.ucn.codecrafters.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ReadClientDto> findAllClients() {

        List<User> userList = this.userRepository.findByRole(Role.CLIENT);
        List<ReadClientDto> clientList = new ArrayList<>();

        for (User user : userList){

            if (user.getDeleted() == Boolean.FALSE){
                ReadClientDto readClientDto = new ReadClientDto();

                readClientDto.setId(user.getId());
                readClientDto.setDni(user.getDni());
                readClientDto.setFirstName(user.getFirstName());
                readClientDto.setLastName(user.getLastName());
                readClientDto.setNationality(user.getNationality());
                readClientDto.setPhone(user.getPhone());

                clientList.add(readClientDto);
            }

        }

        return clientList;
    }

    @Override
    public List<ReadAdministrativeDto> findAllAdministratives() throws Exception {

        try {

            List<User> userList = this.userRepository.findByRole(Role.ADMINISTRATIVE);
            List<ReadAdministrativeDto> administrativeList = new ArrayList<>();

            for (User user : userList){

                if (user.getDeleted() == Boolean.FALSE){

                    ReadAdministrativeDto administrativeDto = new ReadAdministrativeDto();

                    administrativeDto.setId(user.getId());
                    administrativeDto.setDni(user.getDni());
                    administrativeDto.setFirstName(user.getFirstName());
                    administrativeDto.setLastName(user.getLastName());
                    administrativeDto.setEmail(user.getEmail());
                    administrativeDto.setPhone(user.getPhone());

                    administrativeList.add(administrativeDto);
                }

            }

            return administrativeList;

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public ReadClientDto findClientById(Integer id) {

        Optional<User> userFounded = this.userRepository.findById(id);

        if (userFounded.isPresent()){

            User user = userFounded.get();

            ReadClientDto readClientDto = new ReadClientDto();

            readClientDto.setId(user.getId());
            readClientDto.setDni(user.getDni());
            readClientDto.setFirstName(user.getFirstName());
            readClientDto.setLastName(user.getLastName());
            readClientDto.setNationality(user.getNationality());
            readClientDto.setPhone(user.getPhone());

            return readClientDto;
        }

        return null;
    }

    @Override
    public ReadAdministrativeDto findAdministrativeById(Integer id) {
        Optional<User> userFounded = this.userRepository.findById(id);

        if (userFounded.isPresent()){

            User user = userFounded.get();

            ReadAdministrativeDto readAdministrativeDto = new ReadAdministrativeDto();

            readAdministrativeDto.setId(user.getId());
            readAdministrativeDto.setDni(user.getDni());
            readAdministrativeDto.setFirstName(user.getFirstName());
            readAdministrativeDto.setLastName(user.getLastName());
            readAdministrativeDto.setEmail(user.getEmail());
            readAdministrativeDto.setPhone(user.getPhone());

            return readAdministrativeDto;
        }

        return null;
    }

    /**
     * Method responsible for storing an entity in the database.
     *
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    @Override
    public User saveClient(CreateClientDto entity) throws Exception {
        try {

            User user = new User();

            user.setDni(entity.getDni());
            user.setEmail(entity.getEmail());
            user.setFirstName(entity.getFirstName());
            user.setLastName(entity.getLastName());
            user.setPhone(entity.getPhone());
            user.setNationality(entity.getNationality());

            LocalDateTime birthDate = Validation.convertToLocalDate(entity.getBirthDate());
            user.setBirthDate(birthDate);

            // Password encode are the same as the DNI.
            String password = entity.getDni().replace("-", "");
            user.setPassword(this.passwordEncoder.encode(password));
            user.setRole(Role.CLIENT);

            user = this.userRepository.save(user);
            return user;
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
    public User saveAdministrative(CreateAdministrativeDto entity) throws Exception {
        try {

            User user = new User();

            user.setDni(entity.getDni());
            user.setEmail(entity.getEmail());
            user.setFirstName(entity.getFirstName());
            user.setLastName(entity.getLastName());
            user.setPhone(entity.getPhone());
            user.setNationality(entity.getNationality());

            LocalDateTime birthDate = Validation.convertToLocalDate(entity.getBirthDate());
            user.setBirthDate(birthDate);
            // Password encode are the same as the DNI.
            String password = entity.getDni().replace("-", "");
            user.setPassword(this.passwordEncoder.encode(password));
            user.setRole(Role.ADMINISTRATIVE);

            user = this.userRepository.save(user);
            return user;
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
    public boolean userEmailExists(String email) {
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

    @Override
    public ReadClientDto updateClient(Integer id, UpdateClientDto entity) throws Exception {
        try {

            Optional<User> entityOptional = this.userRepository.findById(id);

            if (entityOptional.isPresent()){

                // Update the entity.
                User entityUpdate = entityOptional.get();

                if (entityUpdate.getRole() != Role.CLIENT){
                    throw new Exception("The user isn't a client");
                }

                entityUpdate.setFirstName(entity.getFirstName());
                entityUpdate.setLastName(entity.getLastName());
                entityUpdate.setNationality(entity.getNationality());
                entityUpdate.setPhone(entity.getPhone());

                // Save the user.
                entityUpdate = this.userRepository.save(entityUpdate);


                // Create the ReadClientDto.
                ReadClientDto readClientDto = new ReadClientDto();
                readClientDto.setId(entityUpdate.getId());
                readClientDto.setDni(entityUpdate.getDni());
                readClientDto.setFirstName(entityUpdate.getFirstName());
                readClientDto.setLastName(entityUpdate.getLastName());
                readClientDto.setNationality(entityUpdate.getNationality());
                readClientDto.setPhone(entityUpdate.getPhone());

                return readClientDto;

            }
            else {
                throw new Exception("The item hasn't founded");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public ReadAdministrativeDto updateAdministrative(Integer id, UpdateAdministrativeDto entity) throws Exception {
        try {

            Optional<User> entityOptional = this.userRepository.findById(id);

            if (entityOptional.isPresent()){

                // Update the entity.
                User entityUpdate = entityOptional.get();

                if (entityUpdate.getRole() != Role.ADMINISTRATIVE){
                    throw new Exception("The user isn't a administrative");
                }

                if (userEmailExists(entityUpdate.getEmail())){

                    var user = findUserByEmail(entityUpdate.getEmail());

                    if (!Objects.equals(user.getDni(), entityUpdate.getDni())){
                        throw new Exception("The email already exists");
                    }
                }

                entityUpdate.setFirstName(entity.getFirstName());
                entityUpdate.setLastName(entity.getLastName());
                entityUpdate.setEmail(entity.getEmail());
                entityUpdate.setPhone(entity.getPhone());

                // Save the user.
                entityUpdate = this.userRepository.save(entityUpdate);


                // Create the ReadAdministrativeDto.
                ReadAdministrativeDto readAdministrativeDto = new ReadAdministrativeDto();
                readAdministrativeDto.setId(entityUpdate.getId());
                readAdministrativeDto.setDni(entityUpdate.getDni());
                readAdministrativeDto.setFirstName(entityUpdate.getFirstName());
                readAdministrativeDto.setLastName(entityUpdate.getLastName());
                readAdministrativeDto.setEmail(entityUpdate.getEmail());
                readAdministrativeDto.setPhone(entityUpdate.getPhone());

                return readAdministrativeDto;

            }
            else {
                throw new Exception("The item hasn't founded");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    /**
     * Method responsible for removing an entity from the database.
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
