package cl.ucn.codecrafters.reserve.infraestructure;

import cl.ucn.codecrafters.reserve.domain.ReserveDto;
import cl.ucn.codecrafters.reserve.domain.ReserveError;
import cl.ucn.codecrafters.reserve.application.IReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "reserves")
public class ReserveController {

    @Autowired
    protected IReserveService reserveService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<?> reserveList = this.reserveService.readAllReserves();

            if (reserveList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay reservas por mostrar.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(reserveList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error, por favor intente más tarde.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.reserveService.findById(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ReserveDto entity) {
        try {

            ReserveError reserveError= this.reserveService.validateReserveErrors(entity);

            if(reserveError.getIsValid()){
                return ResponseEntity.status(HttpStatus.CREATED).body(this.reserveService.save(entity));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reserveError);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody ReserveDto entity) {
        try {
            ReserveError reserveError = this.reserveService.validateReserveErrors(entity);

            if (reserveError.getIsValid()){
                return ResponseEntity.status(HttpStatus.OK).body(this.reserveService.update(id, entity));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reserveError);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.reserveService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }
}
