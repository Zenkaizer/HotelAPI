package cl.ucn.codecrafters.reserve.infraestructure;

import cl.ucn.codecrafters.reserve.application.IReserveService;
import cl.ucn.codecrafters.reserve.domain.dtos.CreateReserveDto;
import cl.ucn.codecrafters.reserve.domain.dtos.ReadReserveDto;
import cl.ucn.codecrafters.reserve.domain.dtos.UpdateReserveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "reserves")
public class ReserveController {

    @Autowired
    private IReserveService reserveService;

    /**
     * Method responsible for creating a reserve.
     * @param entity Reserve.
     * @return A response entity with a message.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createReserve(@RequestBody CreateReserveDto entity) {

        try{
            this.reserveService.create(entity);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error, por favor intente más tarde.");
        }
        return ResponseEntity.ok().body("Reserva creada con éxito");
    }

    @GetMapping("")
    public ResponseEntity<?> getAllReserves() {
        try {
            List<ReadReserveDto> reserveList = this.reserveService.readAll();

            if (reserveList == null) {
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
    public ResponseEntity<?> getReserveById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.reserveService.findById(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserveById(@PathVariable Integer id, @RequestBody UpdateReserveDto entity) {
        try {

            this.reserveService.update(id, entity);
            return ResponseEntity.ok().body("Reserva actualizada con éxito.");

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error, por favor intente más tarde.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserveById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.reserveService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error, por favor intente más tarde.");
        }
    }
}
