package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.DifficultLevel;
import com.ctorres.pokequiz.infrastructure.persistence.entity.DifficultLevelEntity;

public final class DifficultLevelMapper {

    public DifficultLevel map(DifficultLevelEntity entity) {
        return new DifficultLevel(
                entity.getId(),
                entity.getDescription(),
                entity.isActive()
        );
    }

    public DifficultLevelEntity map(DifficultLevel domain) {
        return new DifficultLevelEntity(
                domain.id(),
                domain.description(),
                domain.active()
        );
    }
}
