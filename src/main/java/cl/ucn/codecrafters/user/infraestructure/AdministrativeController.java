package cl.ucn.codecrafters.user.infraestructure;
import cl.ucn.codecrafters.user.application.IUserService;
import cl.ucn.codecrafters.user.domain.administrative.CreateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.ReadAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.UpdateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "administratives")
public class AdministrativeController {

    @Autowired
    private IUserService userService;

    /**
     * This method returns all clients for the system.
     * @return A list with all clients.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllAdministrative() {
        try {

            List<?> administrativeList = this.userService.findAllAdministratives();

            if (administrativeList.isEmpty()) {
                return ResponseEntity.badRequest().body("No hay administrativos para mostrar.");
            }

            return ResponseEntity.ok().body(administrativeList);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Hubo un error al obtener los administrativos.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneAdministrative(@PathVariable Integer id) {
        try {

            ReadAdministrativeDto administrativeDto = this.userService.findAdministrativeById(id);

            if (administrativeDto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error, usuario no encontrado.");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(administrativeDto);
            }

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PostMapping("/new-administrative")
    public ResponseEntity<?> createAdministrative(@RequestBody CreateAdministrativeDto entity){
        try {

            if (this.userService.userEmailExists(entity.getEmail())){
                return ResponseEntity.badRequest().body("El correo electrónico ya existe.");
            }

            LocalDateTime now = LocalDateTime.now();

            LocalDateTime birthDate = Validation.convertToLocalDate(entity.getBirthDate());

            // Compare the birth date with today's date.
            if (birthDate.isAfter(now)) {
                return ResponseEntity.badRequest().body("La fecha de nacimiento no puede ser después de hoy.");
            }

            User user = this.userService.saveAdministrative(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Hubo un error en la creación del usuario, intente nuevamente.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdministrative(@PathVariable Integer id, @RequestBody UpdateAdministrativeDto entity) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(this.userService.updateAdministrative(id, entity));
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

