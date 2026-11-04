package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuestionEntity;

import java.util.stream.Collectors;

public final class QuestionMapper {

    private final QuizMapper quizMapper = new QuizMapper();
    private final AnswerMapper answerMapper = new AnswerMapper();
    private final UserAnswerMapper userAnswerMapper = new UserAnswerMapper();

    public Question map(QuestionEntity entity) {

        return Question.restore(
                entity.getId(),
                entity.getDescription(),
                entity.isActive(),
                entity.getFrontImageUrl(),
                entity.getBackImageUrl(),
                quizMapper.map(entity.getQuiz()),
                entity.getAnswers().stream()
                        .map(answerMapper::map)
                        .collect(Collectors.toSet()),
                entity.getUserAnswers().stream()
                        .map(userAnswerMapper::map)
                        .collect(Collectors.toSet())
        );
    }

    public QuestionEntity map(Question domain) {
        return new QuestionEntity(
                domain.id(),
                domain.description(),
                domain.active(),
                domain.frontImageUrl(),
                domain.backImageUrl(),
                quizMapper.map(domain.quiz()),
                domain.answers().stream()
                        .map(answerMapper::map)
                        .collect(Collectors.toSet()),
                domain.userAnswers().stream()
                        .map(userAnswerMapper::map)
                        .collect(Collectors.toSet())
        );
    }
}
