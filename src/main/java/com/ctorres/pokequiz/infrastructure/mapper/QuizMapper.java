package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuizEntity;

public final class QuizMapper {
    private final QuizGraphMapper graph = new QuizGraphMapper();

    public Quiz map(QuizEntity entity) {
        return graph.toDomain(entity);
    }

    public QuizEntity map(Quiz domain) {
        return graph.toEntity(domain);
    }
}