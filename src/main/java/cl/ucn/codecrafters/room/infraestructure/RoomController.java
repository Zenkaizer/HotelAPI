package cl.ucn.codecrafters.room.infraestructure;

import cl.ucn.codecrafters.room.application.IRoomService;
import cl.ucn.codecrafters.room.domain.Room;
import cl.ucn.codecrafters.room.domain.dtos.CreateRoomDto;
import cl.ucn.codecrafters.room.domain.dtos.UpdateRoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "rooms")
public class RoomController {

    @Autowired
    protected IRoomService roomService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            List<?> roomList = this.roomService.findAll();

            if (roomList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body("No hay habitaciones para mostrar.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(roomList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error, por favor intente más tarde.");
        }
    }

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

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomDto entity){

        Room newRoom;

        try{

            newRoom = this.roomService.create(entity);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error, por favor intente más tarde.");
        }
        return ResponseEntity.ok().body(newRoom);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Room entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.roomService.save(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Integer id, @RequestBody UpdateRoomDto entity) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(this.roomService.update(id, entity));

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error, por favor intente más tarde.");
        }
    }

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
