package cl.ucn.codecrafters.controllers;

import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.entities.dto.ClientDto;
import cl.ucn.codecrafters.entities.errors.UserError;
import cl.ucn.codecrafters.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "clients")
@PreAuthorize("hasRole('ROLE_ADMINISTRATIVE')")
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
