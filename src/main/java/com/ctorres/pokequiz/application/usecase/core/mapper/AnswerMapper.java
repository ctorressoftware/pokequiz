package com.ctorres.pokequiz.application.usecase.core.mapper;

import com.ctorres.pokequiz.infrastructure.web.response.quiz.AnswerDto;
import com.ctorres.pokequiz.infrastructure.persistence.entity.Answer;

import java.util.Collection;

public class AnswerMapper {

    public static Collection<AnswerDto> toDto(Collection<Answer> answers) {

        return answers.stream()
                .map(a -> new AnswerDto(
                        a.getDescription(),
                        a.getCanonicalKey()
                ))
                .toList();
    }
}
