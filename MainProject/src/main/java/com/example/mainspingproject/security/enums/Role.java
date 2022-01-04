package com.example.mainspingproject.security.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.MEETUPS_READ, Permission.REPORT_READ, Permission.REPORT_WRITE,Permission.REQUEST_READ,
            Permission.REQUEST_WRITE, Permission.USER_PAGE)),
    ADMIN(Set.of(Permission.MEETUPS_READ, Permission.MEETUPS_WRITE, Permission.REQUEST_SOLVE, Permission.ADMIN_PAGE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
