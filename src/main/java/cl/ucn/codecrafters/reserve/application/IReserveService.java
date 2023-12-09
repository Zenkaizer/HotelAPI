package cl.ucn.codecrafters.reserve.application;


import cl.ucn.codecrafters.reserve.domain.Reserve;
import cl.ucn.codecrafters.reserve.domain.ReserveDto;
import cl.ucn.codecrafters.reserve.domain.ReserveError;
import cl.ucn.codecrafters.reserve.domain.dtos.ReadReserveDto;

import java.util.List;

public interface IReserveService{

    /**
     * Method responsible for listing all entities.
     * @return All corresponding entities in a list.
     * @throws Exception Exception.
     */
    List<ReadReserveDto> readAllReserves() throws Exception;

    /**
     * Method in charge of finding an entity according to its ID.
     * @param id Entity ID.
     * @return An entity according to its ID.
     * @throws Exception Exception.
     */
    Reserve findById(Integer id) throws Exception;

    /**
     * Method responsible for storing an entity in the database.
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    Reserve save(ReserveDto entity) throws Exception;

    /**
     * Method responsible for updating an entity in the database.
     * @param id Entity ID.
     * @param entity Entity.
     * @return The updated entity.
     * @throws Exception Exception.
     */
    Reserve update(Integer id, ReserveDto entity) throws Exception;

    /**
     * Method responsible for removing an entity from the database.
     * @param id Entity ID.
     * @return True if is deleted or False if not.
     * @throws Exception Exception.
     */
    boolean delete(Integer id) throws Exception;


    ReserveError validateReserveErrors(ReserveDto reserve);



}
