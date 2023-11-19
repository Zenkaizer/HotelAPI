package cl.ucn.codecrafters.user.dto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String dni;
    private String firstName;
    private String lastName;
    private String nationality;
    private String phone;

}
