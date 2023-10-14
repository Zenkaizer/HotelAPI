package cl.ucn.codecrafters.entities;

import io.ebean.annotation.NotNull;
import jakarta.persistence.*;

@Entity
@Table(name= "role")
public class Role extends Base{

    @NotNull
    @Column(name = "role_name")
    private String roleName;
}
