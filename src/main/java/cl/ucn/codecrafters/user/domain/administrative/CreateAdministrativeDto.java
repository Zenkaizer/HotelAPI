package cl.ucn.codecrafters.user.domain.administrative;
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
public class CreateAdministrativeDto {

    private String dni;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String nationality;
    private String birthDate;

}
