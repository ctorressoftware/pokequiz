package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.DifficultLevel;

import java.util.Optional;
import java.util.UUID;

public interface DifficultLevelRepository {
    Optional<DifficultLevel> findById(UUID id);
}