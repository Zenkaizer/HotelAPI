package cl.ucn.codecrafters.repositories;

import cl.ucn.codecrafters.entities.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends IBaseRepository<Room, Integer> {
}
