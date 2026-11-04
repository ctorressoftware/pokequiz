package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.UserAnswer;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswerEntity;

public final class UserAnswerMapper {

    private final QuestionMapper questionMapper = new QuestionMapper();

    public UserAnswer map(UserAnswerEntity entity) {
        return new UserAnswer(
                entity.getId(),
                entity.getDescription(),
                entity.getCanonicalKey(),
                entity.isActive(),
                questionMapper.map(entity.getQuestion())
        );
    }

    public UserAnswerEntity map(UserAnswer domain) {
        return new UserAnswerEntity(
                domain.getId(),
                domain.getDescription(),
                domain.getCanonicalKey(),
                domain.isActive(),
                questionMapper.map(domain.getQuestion())
        );
    }
}
