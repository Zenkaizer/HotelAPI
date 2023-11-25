package cl.ucn.codecrafters.reserve.application;

import cl.ucn.codecrafters.reserve.domain.ReserveError;
import cl.ucn.codecrafters.reserve.domain.IReserveRepository;
import cl.ucn.codecrafters.reserve.domain.Reserve;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ReserveService implements IReserveService {

    @Autowired
    private IReserveRepository reserveRepository;

    /**
     * Method responsible for listing all reserves.
     *
     * @return All corresponding reserves in a list.
     * @throws Exception Exception.
     */
    @Override
    public List<Reserve> findAll() throws Exception {

        List<Reserve> reserveList = this.reserveRepository.findAll();

        //TODO: Valida envio null en controlador
        if(reserveList.isEmpty()){
            return null;
        }

        //FIXME: Utilizar el metodo sort con menor complejidad
        reserveList.stream().sorted(Comparator.comparing(Reserve::getReserveDateTime));

        return reserveList;
    }

    /**
     * Method in charge of finding a reserve according to its ID.
     *
     * @param integer Reserve ID.
     * @return A reserve according to its ID.
     * @throws Exception Exception.
     */
    @Override
    public Reserve findById(Integer integer) throws Exception {
        try {
            Optional<Reserve> entity = this.reserveRepository.findById(integer);
            return entity.get();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for storing a reserve in the database.
     *
     * @param entity Reserve.
     * @return The saved reserve.
     * @throws Exception Exception.
     */
    @Override
    public Reserve save(Reserve entity) throws Exception {
        try {
            entity = this.reserveRepository.save(entity);
            return entity;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for updating a reserve in the database.
     *
     * @param integer Entity ID.
     * @param entity  Entity.
     * @return The updated entity.
     * @throws Exception Exception.
     */
    @Override
    public Reserve update(Integer integer, Reserve entity) throws Exception {
        try {
            Optional<Reserve> entityOptional = this.reserveRepository.findById(integer);
            Reserve entityUpdate = entityOptional.get();
            entityUpdate = this.reserveRepository.save(entity);
            return entityUpdate;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Method responsible for removing a reserve from the database.
     *
     * @param integer Reserve ID.
     * @return True if is deleted or False if not.
     * @throws Exception Exception.
     */
    @Override
    public boolean delete(Integer integer) throws Exception {
        try {
            if (this.reserveRepository.existsById(integer)){
                Optional<Reserve> entityOptional = this.reserveRepository.findById(integer);
                Reserve entityDeleted = entityOptional.get();

                entityDeleted.setDeleted(Boolean.TRUE);
                this.reserveRepository.save(entityDeleted);
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

    @Override
    public ReserveError validateReserveErrors(Reserve reserve){

        LocalDateTime actualDateTime = LocalDateTime.now();

        ReserveError reserveErrors = new ReserveError();
        Boolean isValid = Boolean.TRUE;

        //Datetime validations

        if(reserve.getLeaveDateTime().isBefore(reserve.getArriveDateTime())){

            reserveErrors.setLeaveBeforeArriveError("La fecha de salida no puede ser inferior a la fecha de llegada");
            isValid = Boolean.FALSE;
        }

        if(reserve.getArriveDateTime().isBefore(actualDateTime)){

            reserveErrors.setArriveBeforeNowError("La fecha de llegada no puede ser inferior a la actual");
            isValid = Boolean.FALSE;
        }

        if(reserve.getLeaveDateTime().isBefore(actualDateTime)){
            reserveErrors.setArriveBeforeNowError("La fecha de salida no puede ser inferior a la fecha actual");
            isValid = Boolean.FALSE;
        }

        if(reserve.getLeaveDateTime().isBefore(reserve.getReserveDateTime())){
            reserveErrors.setLeaveBeforeReserveTimeError("La fecha de salida no puede ser inferior a la fecha de reserva");
            isValid = Boolean.FALSE;
        }

        if(reserve.getArriveDateTime().isBefore(reserve.getReserveDateTime())){
            reserveErrors.setArriveBeforeReserveTimeError("La fecha de llegada no puede ser inferior a la fecha de la reserva");
            isValid = Boolean.FALSE;
        }

        //Room validations

        try {
            List<Reserve> reserveList = this.findAll();

            for (Reserve r:reserveList) {

                if(r.getRoom().equals(reserve.getRoom()) && (r.getArriveDateTime().equals(reserve.getArriveDateTime()) ||
                        r.getLeaveDateTime().equals(reserve.getLeaveDateTime()))){
                    reserveErrors.setSameReserveIntervalError("Habitación ya reservada para ese intervalo de tiempo");
                    isValid = Boolean.FALSE;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        reserveErrors.setIsValid(isValid);
        return reserveErrors;

    }


}
