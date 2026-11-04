package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.QuizState;
import com.ctorres.pokequiz.infrastructure.persistence.entity.StateEntity;

public final class StateMapper {

    public QuizState map(StateEntity entity) {
        return new QuizState(
                entity.getId(),
                entity.getDescription(),
                entity.getCode(),
                entity.isActive()
        );
    }

    public StateEntity map(QuizState domain) {
        return new StateEntity(
                domain.id(),
                domain.description(),
                domain.code(),
                domain.active()
        );
    }
}