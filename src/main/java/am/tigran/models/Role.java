package am.tigran.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    MANAGER(Set.of(Permission.CHANGE_STATUS, Permission.CHANGE_USER, Permission.DELETE_TASK, Permission.CREATE_TASK, Permission.READ_TASK, Permission.CHANGE_ROLE)),
    EMPLOYEE(Set.of(Permission.CHANGE_STATUS, Permission.CREATE_TASK, Permission.READ_TASK));
    private final Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
