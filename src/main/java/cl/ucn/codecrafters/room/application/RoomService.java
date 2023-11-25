package cl.ucn.codecrafters.room.application;

import cl.ucn.codecrafters.room.domain.RoomError;
import cl.ucn.codecrafters.room.domain.IRoomRepository;
import cl.ucn.codecrafters.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RoomService implements IRoomService {

    @Autowired
    private IRoomRepository roomRepository;

    /**
     * Method responsible for listing all entities.
     *
     * @return All corresponding entities in a list.
     * @throws Exception Exception.
     */
    @Override
    public List<Room> findAll() throws Exception {
        List<Room> roomList = this.roomRepository.findAll();

        if(roomList.isEmpty()){
            return null;
        }

        return roomList;
    }

    /**
     * Method in charge of finding an entity according to its ID.
     *
     * @param integer Entity ID.
     * @return An entity according to its ID.
     * @throws Exception Exception.
     */
    @Override
    public Room findById(Integer integer) throws Exception {
        try {
            Optional<Room> entity = this.roomRepository.findById(integer);
            return entity.get();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for storing an entity in the database.
     *
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    @Override
    public Room save(Room entity) throws Exception {
        try {
            entity = this.roomRepository.save(entity);
            return entity;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for updating an entity in the database.
     *
     * @param integer Entity ID.
     * @param entity  Entity.
     * @return The updated entity.
     * @throws Exception Exception.
     */
    @Override
    public Room update(Integer integer, Room entity) throws Exception {
        try {
            Optional<Room> entityOptional = this.roomRepository.findById(integer);
            Room entityUpdate = entityOptional.get();
            entityUpdate = this.roomRepository.save(entity);
            return entityUpdate;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for removing an entity from the database.
     *
     * @param integer Entity ID.
     * @return True if is deleted or False if not.
     * @throws Exception Exception.
     */
    @Override
    public boolean delete(Integer integer) throws Exception {
        try {
            if (this.roomRepository.existsById(integer)){
                Optional<Room> entityOptional = this.roomRepository.findById(integer);
                Room entityDeleted = entityOptional.get();

                entityDeleted.setDeleted(Boolean.TRUE);
                this.roomRepository.save(entityDeleted);
                return true;
            }
            else {
                throw new Exception("The item hasn't founded");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public RoomError validateRoomErrors(Room room){

        RoomError roomErrors = new RoomError();

        Boolean isValid = Boolean.TRUE;

        //Max capacity validations

        if(room.getMaxCapacity() < 1){

            roomErrors.setNonNaturalMaxCapacityError("La capacidad máxima de la habitación no puede ser menor a 1");
            isValid = Boolean.FALSE;
        }

        if(!(room.getMaxCapacity() instanceof Integer)){
            roomErrors.setInvalidMaxCapacityError("La capacidad máxima de la habitación debe ser numérica");
            isValid = Boolean.FALSE;
        }

        //Price validations

        if(room.getPrice() < 1){

            roomErrors.setNonNaturalPriceError("El precio de la habitación no puede ser menor a 1");
            isValid = Boolean.FALSE;
        }

        if(!(room.getPrice() instanceof Integer)){
            roomErrors.setInvalidPriceError("El precio de la habitacion debe ser numérico");
        }


        //Beds amount validations

        if(room.getDualBeds() < 0 || room.getIndividualBeds() < 0){
            roomErrors.setNegativeBedAmountError("La cantidad de camas no pueden ser menor a 0");
        }

        if(!(room.getDualBeds() instanceof Integer) || !(room.getIndividualBeds() instanceof Integer)){
            roomErrors.setInvalidBedAmountError("La cantidad de camas debe ser numérica");
        }

        roomErrors.setIsValid(isValid);
        return roomErrors;
    }
}
