package cl.ucn.codecrafters.user.domain.dtos;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String dni;
    private String firstName;
    private String lastName;
    private String nationality;
    private String phone;

}
