package cl.ucn.codecrafters.room;

import cl.ucn.codecrafters.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Integer> {
}
