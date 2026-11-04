package com.ctorres.pokequiz.application.usecase.core.mapper;

import com.ctorres.pokequiz.infrastructure.persistence.entity.QuestionEntity;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.QuestionDto;

import java.util.Collection;

public class QuestionMapper {

    public static Collection<QuestionDto> toDto(Collection<QuestionEntity> questionEntities) {

        return questionEntities.stream()
                .map(q -> new QuestionDto(
                        q.getId(),
                        q.getDescription(),
                        q.getFrontImageUrl(),
                        q.getBackImageUrl(),
                        AnswerMapper.toDto(q.getAnswers()),
                       q.getUserAnswers().isEmpty() ? null : UserAnswerMapper.toDto(q.getUserAnswers())
                )).toList();
    }
}
