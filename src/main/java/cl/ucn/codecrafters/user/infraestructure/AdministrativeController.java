package cl.ucn.codecrafters.user.infraestructure;
import cl.ucn.codecrafters.user.application.IUserService;
import cl.ucn.codecrafters.user.domain.administrative.CreateAdministrativeDto;
import cl.ucn.codecrafters.user.domain.administrative.ReadAdministrativeDto;
import cl.ucn.codecrafters.user.domain.entities.User;
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

            ReadAdministrativeDto administrativeDto = null;

            if (administrativeDto == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Error, usuario no encontrado.\"}");
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(administrativeDto);
            }

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createAdministrative(@RequestBody CreateAdministrativeDto entity){
        try {

            User user = this.userService.saveAdministrative(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Hubo un error en la creación del usuario, intente nuevamente.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody User entity) {
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

