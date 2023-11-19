package cl.ucn.codecrafters.utils;

import cl.ucn.codecrafters.utils.Base;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<E extends Base, ID extends Serializable> {

    /**
     * Method responsible for listing all entities.
     * @return All corresponding entities in a list.
     * @throws Exception Exception.
     */
    List<E> findAll() throws Exception;

    /**
     * Method in charge of finding an entity according to its ID.
     * @param id Entity ID.
     * @return An entity according to its ID.
     * @throws Exception Exception.
     */
    E findById(ID id) throws Exception;

    /**
     * Method responsible for storing an entity in the database.
     * @param entity Entity.
     * @return The saved entity.
     * @throws Exception Exception.
     */
    E save(E entity) throws Exception;

    /**
     * Method responsible for updating an entity in the database.
     * @param id Entity ID.
     * @param entity Entity.
     * @return The updated entity.
     * @throws Exception Exception.
     */
    E update(ID id, E entity) throws Exception;

    /**
     * Method responsible for removing an entity from the database.
     * @param id Entity ID.
     * @return True if is deleted or False if not.
     * @throws Exception Exception.
     */
    boolean delete(ID id) throws Exception;

}
