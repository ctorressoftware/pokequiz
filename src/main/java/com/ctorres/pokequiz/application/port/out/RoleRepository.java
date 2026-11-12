package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.auth.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
}
