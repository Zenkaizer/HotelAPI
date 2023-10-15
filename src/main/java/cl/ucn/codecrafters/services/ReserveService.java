package cl.ucn.codecrafters.services;

import cl.ucn.codecrafters.entities.Reserve;
import cl.ucn.codecrafters.repositories.IBaseRepository;
import cl.ucn.codecrafters.repositories.IReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveService extends BaseService<Reserve, Integer> implements IReserveService{

    @Autowired
    private IReserveRepository reserveRepository;

    public ReserveService(IBaseRepository<Reserve, Integer> baseRepository) {
        super(baseRepository);
    }

}
