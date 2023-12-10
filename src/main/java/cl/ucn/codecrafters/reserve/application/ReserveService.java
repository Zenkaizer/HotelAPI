package cl.ucn.codecrafters.reserve.application;

import cl.ucn.codecrafters.reserve.domain.IReserveRepository;
import cl.ucn.codecrafters.reserve.domain.Reserve;
import cl.ucn.codecrafters.reserve.domain.dtos.CreateReserveDto;
import cl.ucn.codecrafters.reserve.domain.dtos.ReadReserveDto;
import cl.ucn.codecrafters.reserve.domain.dtos.UpdateReserveDto;
import cl.ucn.codecrafters.room.application.IRoomService;
import cl.ucn.codecrafters.room.domain.Room;
import cl.ucn.codecrafters.user.application.IUserService;
import cl.ucn.codecrafters.user.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
     * {@inheritDoc}
     */
    @Override
    public List<ReadReserveDto> readAll(){

        List<Reserve> reserveList = this.reserveRepository.findAll();

        List<ReadReserveDto> reserveDtoList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (Reserve reserve : reserveList) {

            ReadReserveDto readReserveDto = new ReadReserveDto();

            String checkIn = dateFormat.format(reserve.getArriveDateTime());
            String checkOut = dateFormat.format(reserve.getLeaveDateTime());

            readReserveDto.setRoomId(reserve.getRoom().getId());
            readReserveDto.setClientRut(reserve.getUser().getDni());
            readReserveDto.setFirstName(reserve.getUser().getFirstName());
            readReserveDto.setLastName(reserve.getUser().getLastName());
            readReserveDto.setCheckIn(checkIn);
            readReserveDto.setCheckOut(checkOut);
            readReserveDto.setConfirmed(reserve.getConfirmed());

            int diffInMillies = Math.toIntExact(Math.abs(reserve.getArriveDateTime().getTime()
                    - reserve.getLeaveDateTime().getTime()));

            Integer days = Math.toIntExact(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));

            readReserveDto.setPrice(reserve.getRoom().getPrice() * days);
            reserveDtoList.add(readReserveDto);
        }

        if(reserveList.isEmpty()){
           return null;
        }

        return reserveDtoList;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(CreateReserveDto entity) throws Exception {

            Reserve reserve = new Reserve();

            if (!validateReserve(-1, entity.getCheckIn(), entity.getCheckOut())) {
                throw new Exception("La reserva no es válida.");
            }

            User user = userService.findUserByEmail(entity.getClientEmail());
            Room room = roomService.findById(entity.getRoomId());

            reserve.setUser(user);
            reserve.setRoom(room);
            reserve.setReserveDateTime(new Date());
            reserve.setArriveDateTime(entity.getCheckIn());
            reserve.setLeaveDateTime(entity.getCheckOut());
            reserve.setConfirmed(Boolean.FALSE);

            this.reserveRepository.save(reserve);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Integer id, UpdateReserveDto entity) throws Exception {

        Reserve reserve = this.findById(id);

        reserve.setRoom(roomService.findById(entity.getRoomId()));
        reserve.setUser(userService.findUserByEmail(entity.getClientEmail()));

        if (!validateReserve(id, entity.getCheckIn(), entity.getCheckOut())) {
            throw new Exception("La reserva no es válida.");
        }

        reserve.setArriveDateTime(entity.getCheckIn());
        reserve.setLeaveDateTime(entity.getCheckOut());
        reserve.setConfirmed(entity.getConfirmed());

        this.reserveRepository.save(reserve);

    }

    /**
     * Method responsible for validating a reservation.
     * @param currentReservationId The current reservation id.
     * @param checkIn The checkIn date.
     * @param checkOut The checkOut date.
     * @return True if the reservation is valid, false otherwise.
     */
    private boolean validateReserve(int currentReservationId, Date checkIn, Date checkOut) {

        // Obtains all the reservations.
        List<Reserve> reserveList = this.reserveRepository.findAll();

        // Iterates over the reservations.
        for (Reserve reservation : reserveList) {

            // Verify if the reservation is the current reservation.
            if (reservation.getId() != currentReservationId) {

                // Verify if the checkIn and checkOut dates are between the reservation dates.
                if (checkIn.before(reservation.getLeaveDateTime()) && checkOut.after(reservation.getArriveDateTime())) {
                    // There is a superposition.
                    return false;
                }

            }

        }

        // No superposition, the reservation is valid.
        return checkIn.before(checkOut);
    }


}
