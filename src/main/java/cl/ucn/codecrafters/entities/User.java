package cl.ucn.codecrafters.entities;

import io.ebean.annotation.NotNull;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user")
public class User extends Base {

    @NotNull
    @Column(name = "rut")
    private String rut;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "nationality")
    private String nationality;

    @NotNull
    @Column(name = "birth_date")
    private Date birthDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Role role;
}
