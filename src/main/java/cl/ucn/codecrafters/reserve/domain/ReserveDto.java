package cl.ucn.codecrafters.reserve.domain;


import cl.ucn.codecrafters.utils.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveDto extends Base {

    private Integer roomNumber;

    private Integer userDni;

    private String firstName;

    private String lastName;

    private LocalDateTime arriveDateTime;

    private LocalDateTime leaveDateTime;

    private Integer price;

    private Boolean confirmed;

}
