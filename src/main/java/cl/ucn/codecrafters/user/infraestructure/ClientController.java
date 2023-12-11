package cl.ucn.codecrafters.user.infraestructure;

import cl.ucn.codecrafters.user.application.IUserService;
import cl.ucn.codecrafters.user.domain.client.UpdateClientDto;
import cl.ucn.codecrafters.user.domain.entities.Role;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.client.ReadClientDto;
import cl.ucn.codecrafters.user.domain.client.CreateClientDto;
import cl.ucn.codecrafters.utils.Validation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "clients")
public class ClientController {

    @Autowired
    private IUserService userService;

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

            ReadClientDto readClientDto = this.userService.findClientById(id);

            if (readClientDto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error, usuario no encontrado.");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(readClientDto);
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error, por favor intente más tarde.");
        }
    }

    @PostMapping("/new-client")
    public ResponseEntity<?> createNewClient(@Valid @RequestBody CreateClientDto createClient) {
        try {

            if (this.userService.userEmailExists(createClient.getEmail())){
                return ResponseEntity.badRequest().body("El correo electrónico ya existe.");
            }

            LocalDateTime now = LocalDateTime.now();

            LocalDateTime birthDate = Validation.convertToLocalDate(createClient.getBirthDate());

            // Compare the birth date with today's date.
            if (birthDate.isAfter(now)) {
                return ResponseEntity.badRequest().body("La fecha de nacimiento no puede ser después de hoy.");
            }

            User user = this.userService.saveClient(createClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error, por favor intente más tarde.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Integer id, @RequestBody UpdateClientDto entity) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(this.userService.updateClient(id, entity));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error, por favor intente más tarde.");
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
