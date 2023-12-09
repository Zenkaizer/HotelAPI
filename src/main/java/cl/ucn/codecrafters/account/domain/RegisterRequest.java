package cl.ucn.codecrafters.account.domain;

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

    private String firstName;

    private String lastName;

    private String phone;

    private String nationality;

    private Date birthDate;

    private String password;

    private String repeatPassword;

}
