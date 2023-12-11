package cl.ucn.codecrafters.reserve.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReserveDto {

    private Integer roomId;
    private String clientEmail;
    private String checkIn;
    private String checkOut;
    private Boolean confirmed;

}
