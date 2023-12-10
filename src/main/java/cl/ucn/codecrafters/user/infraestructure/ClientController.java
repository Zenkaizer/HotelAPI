package cl.ucn.codecrafters.user.infraestructure;

import cl.ucn.codecrafters.user.application.IUserService;
import cl.ucn.codecrafters.user.domain.entities.Role;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.client.ReadClientDto;
import cl.ucn.codecrafters.user.domain.client.CreateClientDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "clients")
public class ClientController {

    @Autowired
    protected IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method returns all clients for the system.
     * @return A list with all clients.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllClients() {
        try {

            List<?> clientList = this.userService.findAllClients();

            if (clientList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay clientes para mostrar.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(clientList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error, por favor intente más tarde.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneClient(@PathVariable Integer id) {
        try {

            ReadClientDto readClientDto = null;
            if (readClientDto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Error, usuario no encontrado.\"}");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(readClientDto);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PostMapping("/new-client")
    public ResponseEntity<?> createNewClient(@Valid @RequestBody CreateClientDto createClient) {
        try {

            if (this.userService.userEmailExists(createClient.getEmail())){
                return ResponseEntity.badRequest().body("El correo electrónico ya existe.");
            }

            if (createClient.getBirthDate().compareTo(new Date()) > 0){
                return ResponseEntity.badRequest().body("La fecha de nacimiento no puede ser después de hoy.");
            }


            User user = new User();

            user.setDni(createClient.getDni());
            user.setEmail(createClient.getEmail());
            // Remove the - character for no spaces.
            user.setPassword(passwordEncoder.encode(createClient.getDni().replace("-", "")));
            user.setFirstName(createClient.getFirstName());
            user.setLastName(createClient.getLastName());
            user.setPhone(createClient.getPhone());
            user.setNationality(createClient.getNationality());
            user.setBirthDate(createClient.getBirthDate());
            user.setRole(Role.CLIENT);

            this.userService.saveClient(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamete");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id, @RequestBody User entity) {
        try {



            return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(id, entity));

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.userService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }


}
