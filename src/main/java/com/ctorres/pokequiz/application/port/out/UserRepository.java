package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.auth.User;

import java.util.UUID;

public interface UserRepository {
    User getReferenceById(UUID id);
}