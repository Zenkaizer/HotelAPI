package cl.ucn.codecrafters.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cl.ucn.codecrafters.user.Permission.*;

@RequiredArgsConstructor
public enum Role{

    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_DELETE,
                    ADMIN_UPDATE,
                    ADMINISTRATIVE_READ,
                    ADMINISTRATIVE_CREATE,
                    ADMINISTRATIVE_DELETE,
                    ADMINISTRATIVE_UPDATE
            )
    ),
    ADMINISTRATIVE(
            Set.of(
                    ADMINISTRATIVE_READ,
                    ADMINISTRATIVE_CREATE,
                    ADMINISTRATIVE_DELETE,
                    ADMINISTRATIVE_UPDATE
            )
    ),
    CLIENT(
            Set.of(
                    CLIENT_CREATE,
                    CLIENT_DELETE,
                    CLIENT_UPDATE,
                    CLIENT_READ
            )
    )

    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){

        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }


}
