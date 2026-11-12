package com.ctorres.pokequiz.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import com.ctorres.pokequiz.infrastructure.persistence.entity.UserEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsernameAndActiveTrue(String username);
    boolean existsByUsername(String username);
    UserEntity getByUsername(String username);
    UserEntity getById(@NonNull UUID id);
}