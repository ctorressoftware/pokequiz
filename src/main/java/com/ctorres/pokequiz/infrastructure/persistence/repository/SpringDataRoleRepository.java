package com.ctorres.pokequiz.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ctorres.pokequiz.infrastructure.persistence.entity.RoleEntity;

public interface SpringDataRoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(String name);
}
