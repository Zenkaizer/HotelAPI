package cl.ucn.codecrafters.services;

import cl.ucn.codecrafters.entities.Base;
import cl.ucn.codecrafters.repositories.IBaseRepository;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<E extends Base, ID extends Serializable> implements IBaseService<E, ID> {

    protected IBaseRepository<E, ID> baseRepository;

    public BaseService(IBaseRepository<E, ID> baseRepository){
        this.baseRepository = baseRepository;
    }


    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            List<E> entities = this.baseRepository.findAll();
            return entities;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entity = this.baseRepository.findById(id);
            return entity.get();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            entity = this.baseRepository.save(entity);
            return entity;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            Optional<E> entityOptional = this.baseRepository.findById(id);
            E entityUpdate = entityOptional.get();
            entityUpdate = this.baseRepository.save(entity);
            return entityUpdate;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            if (this.baseRepository.existsById(id)){
                Optional<E> entityOptional = this.baseRepository.findById(id);
                E entityDeleted = entityOptional.get();

                entityDeleted.setDeleted(Boolean.TRUE);
                this.baseRepository.save(entityDeleted);
                return true;
            }
            else {
                throw new Exception("The item hasn't founded");
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
