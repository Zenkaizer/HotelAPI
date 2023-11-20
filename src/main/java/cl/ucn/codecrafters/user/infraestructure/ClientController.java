package cl.ucn.codecrafters.user.infraestructure;

import cl.ucn.codecrafters.user.application.IUserService;
import cl.ucn.codecrafters.user.domain.User;
import cl.ucn.codecrafters.user.domain.UserError;
import cl.ucn.codecrafters.user.domain.ClientDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "clients")
public class ClientController {

    @Autowired
    protected IUserService userService;

    /**
     * This method returns all clients for the system.
     * @return A list with all clients.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllClients() {
        try {

            List<?> clientList = this.userService.findAllClients();

            if (clientList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\":\"No hay clientes para mostrar.\"}");
            }

            return ResponseEntity.status(HttpStatus.OK).body(clientList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneClient(@PathVariable Integer id) {
        try {

            ClientDto clientDto = this.userService.findUserDtoById(id);
            if (clientDto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Error, usuario no encontrado.\"}");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(clientDto);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody User entity) {
        try {

            UserError userError = this.userService.validateUserErrors(entity);

            if (userError.getIsValid()){
                return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveClient(entity));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userError);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody User entity) {
        try {
            UserError userError = this.userService.validateUserErrors(entity);

            if (userError.getIsValid()){
                return ResponseEntity.status(HttpStatus.OK).body(this.userService.update(id, entity));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userError);
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
