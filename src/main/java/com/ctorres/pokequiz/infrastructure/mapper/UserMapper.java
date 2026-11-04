package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.auth.Role;
import com.ctorres.pokequiz.domain.model.auth.User;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserEntity;
import com.ctorres.pokequiz.infrastructure.security.AuthUser;

import java.util.UUID;
import java.util.stream.Collectors;

public final class UserMapper {

    private final RoleMapper roleMapper = new RoleMapper();

    public User map(UserEntity entity) {
        return User.restore(
                entity.getId(),
                entity.getUsername(),
                entity.getPasswordHash(),
                entity.isActive(),
                entity.getRoles().stream()
                        .map(roleMapper::map)
                        .collect(Collectors.toSet())
        );
    }

    public UserEntity map(User domain) {
        return new UserEntity(
                domain.id(),
                domain.username(),
                domain.passwordHash(),
                domain.active(),
                domain.roles().stream()
                        .map(roleMapper::map)
                        .collect(Collectors.toSet())
        );
    }

    public User map(AuthUser authUser) {
        return User.restore(
                authUser.getId(),
                authUser.getUsername(),
                authUser.getPassword(),
                authUser.isEnabled(),
                authUser.getAuthorities().stream().map(
                        role ->
                                new Role( // TODO: must create security Role object implementing GrantedAuthority
                                        UUID.randomUUID(),
                                        role.getAuthority(),
                                        "",
                                        true
                                )
                ).collect(Collectors.toSet())
        );
    }
}
