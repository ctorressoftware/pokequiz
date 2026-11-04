package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuizEntity;

import java.util.stream.Collectors;

public final class QuizMapper {
    private final StateMapper stateMapper = new StateMapper();
    private final DifficultLevelMapper difficultLevelMapper = new DifficultLevelMapper();
    private final UserMapper userMapper = new UserMapper();
    private final QuestionMapper questionMapper = new QuestionMapper();

    public Quiz map(QuizEntity entity) {
        return Quiz.restore(
                entity.getId(),
                entity.getInitialDate(),
                entity.getEndDate(),
                stateMapper.map(entity.getState()),
                difficultLevelMapper.map(entity.getDifficultLevel()),
                userMapper.map(entity.getUser()),
                entity.getQuestions().stream()
                        .map(questionMapper::map)
                        .collect(Collectors.toSet())
        );
    }

    public QuizEntity map(Quiz domain) {
        return new QuizEntity(
                domain.getId(),
                domain.getStartDate(),
                domain.getEndDate(),
                stateMapper.map(domain.getState()),
                difficultLevelMapper.map(domain.getDifficultLevel()),
                userMapper.map(domain.getUser())
        );
    }
}