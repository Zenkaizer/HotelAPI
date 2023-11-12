package cl.ucn.codecrafters.controllers;
import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.entities.dto.AdministrativeDto;
import cl.ucn.codecrafters.entities.errors.UserError;
import cl.ucn.codecrafters.exceptions.NoFoundUserException;
import cl.ucn.codecrafters.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "administratives")
public class AdministrativeController {

    @Autowired
    protected IUserService userService;

    /**
     * This method returns all clients for the system.
     * @return A list with all clients.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllAdministrative() {
        try {

            List<?> administrativeList = this.userService.findAllAdministratives();

            if (administrativeList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\":\"No hay administrativos para mostrar.\"}");
            }

            return ResponseEntity.status(HttpStatus.OK).body(administrativeList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAdministrative(@PathVariable Integer id) {
        try {

            AdministrativeDto administrativeDto = this.userService.findUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(administrativeDto);
        }
        catch (NoFoundUserException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody User entity) {
        try {

            UserError userError = this.userService.validateUserErrors(entity);

            if (userError.getIsValid()){
                return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(entity));
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

