package cl.ucn.codecrafters.user.domain.administrative;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdministrativeDto {

    private Integer id;
    private String dni;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}