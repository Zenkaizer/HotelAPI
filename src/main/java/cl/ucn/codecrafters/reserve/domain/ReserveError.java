package cl.ucn.codecrafters.reserve.domain;

import cl.ucn.codecrafters.utils.BaseError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReserveError extends BaseError {

    private String leaveBeforeArriveError;

    private String arriveBeforeNowError;

    private String leaveBeforeNowError;

    private String leaveBeforeReserveTimeError;

    private String arriveBeforeReserveTimeError;

    private String sameReserveIntervalError;

}
