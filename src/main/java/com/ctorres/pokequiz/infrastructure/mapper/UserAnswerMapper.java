package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.UserAnswer;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswerEntity;

public final class UserAnswerMapper {
    private final QuizGraphMapper graph = new QuizGraphMapper();

    public UserAnswer map(UserAnswerEntity entity) {
        return graph.toDomain(entity);
    }

    public UserAnswerEntity map(UserAnswer domain) {
        return graph.toEntity(domain);
    }
}
