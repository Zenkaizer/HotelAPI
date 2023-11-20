package cl.ucn.codecrafters.controllers;

import cl.ucn.codecrafters.entities.Reserve;
import cl.ucn.codecrafters.entities.errors.ReserveError;
import cl.ucn.codecrafters.services.interfaces.IReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "reserves")
public class ReserveController implements IBaseController<Reserve, Integer>{

    @Autowired
    private IReserveService reserveService;

    @Override
    @GetMapping()
    public ResponseEntity<?> getAll() {
        try {
            List<?> reserveList = this.reserveService.findAll();

            if (reserveList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\":\"No hay reservas por mostrar.\"}");
            }

            return ResponseEntity.status(HttpStatus.OK).body(reserveList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @Override
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

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Reserve entity) {
        try {

            ReserveError reserveError= this.reserveService.validateReserveErrors(entity);

            if(reserveError.getIsValid()){
                return ResponseEntity.status(HttpStatus.OK).body(this.reserveService.save(entity));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reserveError);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody Reserve entity) {
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

    @Override
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
