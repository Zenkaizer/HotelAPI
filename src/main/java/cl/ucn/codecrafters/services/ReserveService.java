package cl.ucn.codecrafters.services;

import cl.ucn.codecrafters.entities.Reserve;
import cl.ucn.codecrafters.repositories.IReserveRepository;
import cl.ucn.codecrafters.services.interfaces.IReserveService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ReserveService implements IReserveService {

    @Autowired
    private IReserveRepository reserveRepository;

    /**
     * Method responsible for listing all entities.
     *
     * @return All corresponding entities in a list.
     * @throws Exception Exception.
     */
    @Override
    public List<Reserve> findAll() throws Exception {
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
    public Reserve findById(Integer integer) throws Exception {
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
    public Reserve save(Reserve entity) throws Exception {
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
    public Reserve update(Integer integer, Reserve entity) throws Exception {
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
