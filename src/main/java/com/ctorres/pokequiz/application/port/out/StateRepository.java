package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.QuizState;

import java.util.UUID;

public interface StateRepository {
    QuizState getReferenceById(UUID id);
}
