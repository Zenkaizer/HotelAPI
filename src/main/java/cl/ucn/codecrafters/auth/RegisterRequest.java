package cl.ucn.codecrafters.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String dni;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String nationality;

    private Date birthDate;

}
