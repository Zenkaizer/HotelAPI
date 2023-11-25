package cl.ucn.codecrafters.reserve.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReserve {

    private Integer roomId;

    private String userId;

    private String firstName;

    private String lastName;

    private LocalDateTime arriveDateTime;

    private LocalDateTime leaveDateTime;

    private Boolean confirmed;

}
