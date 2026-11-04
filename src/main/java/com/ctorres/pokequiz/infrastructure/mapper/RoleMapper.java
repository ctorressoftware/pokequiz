package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.auth.Role;
import com.ctorres.pokequiz.infrastructure.persistence.entity.RoleEntity;

public final class RoleMapper {

    public Role map(RoleEntity entity) {
        return new Role(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.isActive()
        );
    }

    public RoleEntity map(Role domain) {
        return new RoleEntity(
                domain.id(),
                domain.name(),
                domain.description(),
                domain.active()
        );
    }
}
