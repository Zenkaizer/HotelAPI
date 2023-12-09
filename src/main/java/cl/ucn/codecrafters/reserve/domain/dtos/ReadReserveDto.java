package cl.ucn.codecrafters.reserve.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadReserveDto {

    private Integer roomId;
    private String clientRut;
    private String firstName;
    private String lastName;
    private String checkIn;
    private String checkOut;
    private Integer price;
    private Boolean confirmed;

}
