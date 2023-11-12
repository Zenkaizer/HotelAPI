package cl.ucn.codecrafters.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends Base implements UserDetails {

    @NotNull
    @NotEmpty
    @Column(name = "rut")
    private String dni;

    @NotNull
    @Email(message = "El correo electrónico es inválido.")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "La contraseña no cumple con los requisitos mínimos.")
    @Column(name = "password")
    private String password;

    @NotNull(message = "El campo no puede ser nulo.")
    @NotEmpty(message = "El campo no puede estar vacío.")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "El campo no puede ser nulo.")
    @NotEmpty(message = "El campo no puede estar vacío.")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "El campo no puede ser nulo.")
    @NotEmpty(message = "El campo no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{8,}$", message = "El teléfono debe contener al menos 8 dígitos y debe contener solo números.")
    @Column(name = "phone")
    private String phone;

    @NotNull
    @NotBlank
    @Column(name = "nationality")
    private String nationality;

    @NotNull
    @Past(message = "La fecha de nacimiento no puede ser posterior a la actual.")
    @Column(name = "birth_date")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
