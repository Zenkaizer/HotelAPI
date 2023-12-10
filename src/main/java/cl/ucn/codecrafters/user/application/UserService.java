package cl.ucn.codecrafters.user.application;

import cl.ucn.codecrafters.user.domain.administrative.CreateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.ReadAdministrativeDto;
import cl.ucn.codecrafters.user.domain.client.ReadClientDto;
import cl.ucn.codecrafters.user.domain.entities.Role;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ReadClientDto> findAllClients() {

        List<User> userList = this.userRepository.findByRole(Role.CLIENT);
        List<ReadClientDto> clientList = new ArrayList<>();

        if (userList.isEmpty()){
            //TODO: Cambiar
            System.out.println("[!] No existen usuarios en el sistema [!]");
        }

        for (User user : userList){

            ReadClientDto readClientDto = new ReadClientDto();

            readClientDto.setId(user.getId());
            readClientDto.setDni(user.getDni());
            readClientDto.setFirstName(user.getFirstName());
            readClientDto.setLastName(user.getLastName());
            readClientDto.setNationality(user.getNationality());
            readClientDto.setPhone(user.getPhone());

            clientList.add(readClientDto);

        }

        return clientList;
    }

    @Override
    public List<ReadAdministrativeDto> findAllAdministratives() throws Exception {

        try {

            List<User> userList = this.userRepository.findByRole(Role.ADMINISTRATIVE);
            List<ReadAdministrativeDto> administrativeList = new ArrayList<>();

            for (User user : userList){

                ReadAdministrativeDto administrativeDto = new ReadAdministrativeDto();

                administrativeDto.setDni(user.getDni());
                administrativeDto.setFirstName(user.getFirstName());
                administrativeDto.setLastName(user.getLastName());
                administrativeDto.setEmail(user.getEmail());
                administrativeDto.setPhone(user.getPhone());

                administrativeList.add(administrativeDto);

            }

            return administrativeList;

        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

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
    public User saveAdministrative(CreateAdministrativeDto entity) throws Exception {
        try {

            User user = new User();

            user.setDni(entity.getDni());
            user.setEmail(entity.getEmail());
            user.setFirstName(entity.getFirstName());
            user.setLastName(entity.getLastName());
            user.setPhone(entity.getPhone());
            user.setNationality(entity.getNationality());
            user.setBirthDate(entity.getBirthDate());
            // Password encode are the same as the DNI.
            user.setPassword(this.passwordEncoder.encode(entity.getDni()));
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
