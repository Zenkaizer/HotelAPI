package cl.ucn.codecrafters.services;

import cl.ucn.codecrafters.entities.Room;
import cl.ucn.codecrafters.repositories.IBaseRepository;
import cl.ucn.codecrafters.repositories.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends BaseService<Room, Integer> implements IRoomService{

    @Autowired
    private IRoomRepository roomRepository;

    public RoomService(IBaseRepository<Room, Integer> baseRepository) {
        super(baseRepository);
    }
}
