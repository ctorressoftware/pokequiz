package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.Answer;
import com.ctorres.pokequiz.infrastructure.persistence.entity.AnswerEntity;

public final class AnswerMapper {
    private final QuizGraphMapper graph = new QuizGraphMapper();

    public Answer map(AnswerEntity entity) {
        return graph.toDomain(entity);
    }

    public AnswerEntity map(Answer domain) {
        return graph.toEntity(domain);
    }
}
