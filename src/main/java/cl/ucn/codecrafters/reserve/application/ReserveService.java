package cl.ucn.codecrafters.reserve.application;

import cl.ucn.codecrafters.reserve.domain.ReserveDto;
import cl.ucn.codecrafters.reserve.domain.ReserveError;
import cl.ucn.codecrafters.reserve.domain.IReserveRepository;
import cl.ucn.codecrafters.reserve.domain.Reserve;
import cl.ucn.codecrafters.room.application.IRoomService;
import cl.ucn.codecrafters.room.domain.Room;
import cl.ucn.codecrafters.user.application.IUserService;
import cl.ucn.codecrafters.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ReserveService implements IReserveService {

    @Autowired
    private IReserveRepository reserveRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoomService roomService;

    /**
     * Method responsible for listing all reserves.
     *
     * @return All corresponding reserves in a list.
     */
    @Override
    public List<Reserve> findAll(){

        List<Reserve> reserveList = this.reserveRepository.findAll();

        if(reserveList.isEmpty()){
           return new ArrayList<>();
        }

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
    public Reserve save(ReserveDto entity) throws Exception {
        try {

            Reserve reserve = new Reserve();
            LocalDateTime dateTime = LocalDateTime.now();

            User user = userService.findUserById(entity.getUserDni());
            Room room = roomService.findById(entity.getRoomNumber());

            reserve.setUser(user);
            reserve.setRoom(room);
            reserve.setReserveDateTime(dateTime);
            reserve.setArriveDateTime(entity.getArriveDateTime());
            reserve.setLeaveDateTime(entity.getLeaveDateTime());
            reserve.setConfirmed(entity.getConfirmed());

            reserve = this.reserveRepository.save(reserve);
            return reserve;
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
    public Reserve update(Integer integer, ReserveDto entity) throws Exception {
        try {
            Optional<Reserve> entityOptional = this.reserveRepository.findById(integer);
            Reserve entityUpdate = entityOptional.get();

            LocalDateTime dateTime = LocalDateTime.now();
            Reserve reserve = new Reserve();

            reserve.setUser(userService.findUserById(entity.getUserDni()));
            reserve.setRoom(roomService.findById(entity.getRoomNumber()));
            reserve.setReserveDateTime(dateTime);
            reserve.setArriveDateTime(entity.getArriveDateTime());
            reserve.setLeaveDateTime(entity.getLeaveDateTime());
            reserve.setConfirmed(entity.getConfirmed());
            entityUpdate = this.reserveRepository.save(reserve);
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
    public ReserveError validateReserveErrors(ReserveDto reserve){

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

        /*
        if(reserve.getLeaveDateTime().isBefore(reserve.getReserveDateTime())){
            reserveErrors.setLeaveBeforeReserveTimeError("La fecha de salida no puede ser inferior a la fecha de reserva");
            isValid = Boolean.FALSE;
        }

        if(reserve.getArriveDateTime().isBefore(reserve.getReserveDateTime())){
            reserveErrors.setArriveBeforeReserveTimeError("La fecha de llegada no puede ser inferior a la fecha de la reserva");
            isValid = Boolean.FALSE;
        }
        */

        //Room validations

        try {
            List<Reserve> reserveList = this.findAll();

            for (Reserve r:reserveList) {

                if(r.getRoom().getId().equals(reserve.getRoomNumber()) && (r.getArriveDateTime().equals(reserve.getArriveDateTime()) ||
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
