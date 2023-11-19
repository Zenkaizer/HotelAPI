package cl.ucn.codecrafters.utils;

import cl.ucn.codecrafters.utils.Base;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface IBaseController<E extends Base, ID extends Serializable> {

    ResponseEntity<?> getAll();
    ResponseEntity<?> getOne(@PathVariable ID id);
    ResponseEntity<?> save(@RequestBody E entity);
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);
    ResponseEntity<?> delete(@PathVariable ID id);

}
