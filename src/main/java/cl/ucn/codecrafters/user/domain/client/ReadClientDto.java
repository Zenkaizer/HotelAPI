package cl.ucn.codecrafters.user.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReadClientDto {

    private Integer id;
    private String dni;
    private String firstName;
    private String lastName;
    private String nationality;
    private String phone;

}
