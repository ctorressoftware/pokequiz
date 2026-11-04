package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.Answer;
import com.ctorres.pokequiz.infrastructure.persistence.entity.AnswerEntity;

public final class AnswerMapper {

    private final QuestionMapper questionMapper = new QuestionMapper();

    public Answer map(AnswerEntity entity) {
        return new Answer(
                entity.getId(),
                entity.getDescription(),
                entity.getCanonicalKey(),
                entity.isCorrect(),
                entity.isActive(),
                questionMapper.map(entity.getQuestion())
        );
    }

    public AnswerEntity map(Answer domain) {
        return new AnswerEntity(
                domain.getId(),
                domain.getDescription(),
                domain.getCanonicalKey(),
                domain.isCorrect(),
                domain.isActive(),
                questionMapper.map(domain.getQuestion())
        );
    }
}
