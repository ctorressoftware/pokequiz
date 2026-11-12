package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.auth.User;

import java.util.UUID;

public interface UserRepository {
    User getReferenceById(UUID id);
    User getByUsername(String username);
    User getById(UUID id);
    User save(User user);
}