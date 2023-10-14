package cl.ucn.codecrafters.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name= "role")
public class Role extends Base{

    @NotNull
    @Column(name = "role_name")
    private String roleName;
}
