package cl.ucn.codecrafters.room.application;

import cl.ucn.codecrafters.room.domain.IRoomRepository;
import cl.ucn.codecrafters.room.domain.Room;
import cl.ucn.codecrafters.room.domain.dtos.CreateRoomDto;
import cl.ucn.codecrafters.room.domain.dtos.ReadRoomDto;
import cl.ucn.codecrafters.room.domain.dtos.UpdateRoomDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
     */
    @Override
    public List<ReadRoomDto> findAll() {

        List<Room> roomList = this.roomRepository.findAll();

        List<ReadRoomDto> roomDtoList = new ArrayList<>();

        for (Room room : roomList) {

            if (room.getDeleted() == Boolean.FALSE){

                ReadRoomDto roomDto = new ReadRoomDto();

                roomDto.setId(room.getId());
                roomDto.setIndividualBeds(room.getIndividualBeds());
                roomDto.setDualBeds(room.getDualBeds());
                roomDto.setMaxCapacity(room.getMaxCapacity());
                roomDto.setHaveBathroom(room.getHaveBathroom());
                roomDto.setPrice(room.getPrice());

                roomDtoList.add(roomDto);
            }
        }

        return roomDtoList;
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

    @Override
    public Room create(CreateRoomDto entity) throws Exception {
        try{
            Room room = new Room();

            room.setIndividualBeds(entity.getIndividualBeds());
            room.setDualBeds(entity.getDualBeds());
            room.setMaxCapacity(entity.getMaxCapacity());

            if (entity.getHaveBathroom().equals("Si")){
                room.setHaveBathroom(Boolean.TRUE);
            }
            else{
                room.setHaveBathroom(Boolean.FALSE);
            }

            room.setPrice(entity.getPrice());

            return this.roomRepository.save(room);

        }
        catch (Exception e){
            throw new Exception("Error al crear la habitación");
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
    public Room update(Integer integer, UpdateRoomDto entity) throws Exception {
        try {

            Optional<Room> entityOptional = this.roomRepository.findById(integer);

            if (entityOptional.isEmpty()){
                throw new Exception("La habitación no existe.");
            }

            Room entityUpdated = entityOptional.get();

            entityUpdated.setIndividualBeds(entity.getIndividualBeds());
            entityUpdated.setDualBeds(entity.getDualBeds());
            entityUpdated.setMaxCapacity(entity.getMaxCapacity());
            entityUpdated.setHaveBathroom(entity.getHaveBathroom());
            entityUpdated.setPrice(entity.getPrice());

            return this.roomRepository.save(entityUpdated);
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

}
