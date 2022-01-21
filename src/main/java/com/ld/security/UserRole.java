package com.ld.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.ld.security.Permission.DELETE;
import static com.ld.security.Permission.MANAGE;
import static com.ld.security.Permission.MODIFY;
import static com.ld.security.Permission.READ;
import static com.ld.security.Permission.WRITE;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_ADMIN(Set.of(READ, WRITE, MODIFY, DELETE, MANAGE)),
    ROLE_MASTER(Set.of(READ, WRITE, MODIFY, DELETE)),
    ROLE_USER(Set.of(READ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> authorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
