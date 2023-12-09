package cl.ucn.codecrafters.user.domain.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    CLIENT_READ("client:read"),
    CLIENT_CREATE("client:create"),
    CLIENT_UPDATE("client:update"),
    CLIENT_DELETE("client:delete"),

    ADMINISTRATIVE_READ("administrative:read"),
    ADMINISTRATIVE_CREATE("administrative:create"),
    ADMINISTRATIVE_UPDATE("administrative:update"),
    ADMINISTRATIVE_DELETE("administrative:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    ;

    @Getter
    private final String permission;



}
