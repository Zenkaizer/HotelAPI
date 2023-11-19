package cl.ucn.codecrafters.room.application;

import cl.ucn.codecrafters.room.domain.IRoomRepository;
import cl.ucn.codecrafters.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return false;
    }
}
