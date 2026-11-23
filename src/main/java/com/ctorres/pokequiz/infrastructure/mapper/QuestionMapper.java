package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuestionEntity;

public final class QuestionMapper {
    private final QuizGraphMapper graph = new QuizGraphMapper();

    public Question map(QuestionEntity entity) {
        return graph.toDomain(entity);
    }

    public QuestionEntity map(Question domain) {
        return graph.toEntity(domain);
    }
}
