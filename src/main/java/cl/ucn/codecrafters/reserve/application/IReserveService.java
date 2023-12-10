package cl.ucn.codecrafters.reserve.application;


import cl.ucn.codecrafters.reserve.domain.Reserve;
import cl.ucn.codecrafters.reserve.domain.dtos.CreateReserveDto;
import cl.ucn.codecrafters.reserve.domain.dtos.ReadReserveDto;
import cl.ucn.codecrafters.reserve.domain.dtos.UpdateReserveDto;

import java.util.List;

public interface IReserveService{

    /**
     * Method responsible for listing all entities.
     * @return All corresponding entities in a list.
     * @throws Exception Exception.
     */
    List<ReadReserveDto> readAll() throws Exception;

    /**
     * Method in charge of finding an entity according to its ID.
     * @param id Entity ID.
     * @return An entity according to its ID.
     * @throws Exception Exception.
     */
    Reserve findById(Integer id) throws Exception;

    /**
     * Method responsible for removing an entity from the database.
     * @param id Entity ID.
     * @return True if is deleted or False if not.
     * @throws Exception Exception.
     */
    boolean delete(Integer id) throws Exception;

    /**
     * Method responsible for creating an entity in the database.
     * @param entity Reserve entity.
     * @throws Exception Exception.
     */
    void create(CreateReserveDto entity) throws Exception;

    /**
     * Method responsible for updating an entity in the database.
     * @param id Entity ID.
     * @param entity Reserve entity.
     * @throws Exception Exception.
     */
    void update(Integer id, UpdateReserveDto entity) throws Exception;

}
