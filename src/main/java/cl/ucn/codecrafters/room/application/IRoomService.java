package cl.ucn.codecrafters.room.application;

import cl.ucn.codecrafters.room.domain.Room;
import cl.ucn.codecrafters.room.domain.RoomError;
import cl.ucn.codecrafters.room.domain.dtos.CreateRoomDto;

import java.util.List;

public interface IRoomService{

    /**
     * Method responsible for listing all entities.
     * @return All corresponding entities in a list.
     * @throws Exception Exception.
     */
    List<Room> findAll() throws Exception;

    /**
     * Method in charge of finding an entity according to its ID.
     * @param id Entity ID.
     * @return An entity according to its ID.
     * @throws Exception Exception.
     */
    Room findById(Integer id) throws Exception;

    Room create(CreateRoomDto entity) throws Exception;

    /**
     * Method responsible for storing an entity in the database.
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    Room save(Room entity) throws Exception;

    /**
     * Method responsible for updating an entity in the database.
     * @param id Entity ID.
     * @param entity Entity.
     * @return The updated entity.
     * @throws Exception Exception.
     */
    Room update(Integer id, Room entity) throws Exception;

    /**
     * Method responsible for removing an entity from the database.
     * @param id Entity ID.
     * @return True if is deleted or False if not.
     * @throws Exception Exception.
     */
    boolean delete(Integer id) throws Exception;

    RoomError validateRoomErrors(Room room);

}
