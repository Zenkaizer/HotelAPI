package cl.ucn.codecrafters.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserError extends BaseError{

    private String dniError;

    private String emailError;

    private String passwordError;

    private String firstNameError;

    private String lastNameError;

    private String phoneError;

    private String nationalityError;

    private String birthDateError;


}
