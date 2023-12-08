package cl.ucn.codecrafters.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {

    private String actualEmail;

    private String email;

    private String password;

    private String confirmPassword;

}
