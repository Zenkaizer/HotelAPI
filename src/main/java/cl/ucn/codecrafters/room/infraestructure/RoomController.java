package cl.ucn.codecrafters.room.infraestructure;


import cl.ucn.codecrafters.room.domain.RoomError;
import cl.ucn.codecrafters.room.application.IRoomService;
import cl.ucn.codecrafters.room.domain.Room;
import cl.ucn.codecrafters.utils.IBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "rooms")
public class RoomController implements IBaseController<Room, Integer> {

    @Autowired
    protected IRoomService roomService;

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<?> roomList = this.roomService.findAll();

            if (roomList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"error\":\"No hay habitaciones para mostrar.\"}");
            }

            return ResponseEntity.status(HttpStatus.OK).body(roomList);
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
            return ResponseEntity.status(HttpStatus.OK).body(this.roomService.findById(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Room entity) {
        try {

            RoomError roomError = this.roomService.validateRoomErrors(entity);

            if(roomError.getIsValid()){
                return ResponseEntity.status(HttpStatus.OK).body(this.roomService.save(entity));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(roomError);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Room entity) {
        try {

            RoomError roomError = this.roomService.validateRoomErrors(entity);

            if(roomError.getIsValid()){
                return ResponseEntity.status(HttpStatus.OK).body(this.roomService.update(id, entity));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(roomError);

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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(this.roomService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }
}
