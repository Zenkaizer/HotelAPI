package cl.ucn.codecrafters.user.domain.client;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * This class references a client created by an Administrator or Admin.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientDto {

    private String dni;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String nationality;
    private String birthDate;

}
