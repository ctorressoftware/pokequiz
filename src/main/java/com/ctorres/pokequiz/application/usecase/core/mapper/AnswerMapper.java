package com.ctorres.pokequiz.application.usecase.core.mapper;

import com.ctorres.pokequiz.infrastructure.persistence.entity.AnswerEntity;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.AnswerDto;

import java.util.Collection;

public class AnswerMapper {

    public static Collection<AnswerDto> toDto(Collection<AnswerEntity> answerEntities) {

        return answerEntities.stream()
                .map(a -> new AnswerDto(
                        a.getDescription(),
                        a.getCanonicalKey()
                ))
                .toList();
    }
}
