package com.ctorres.pokequiz.application.usecase.core.mapper;

import com.ctorres.pokequiz.infrastructure.web.response.quiz.UserAnswerDto;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswer;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserAnswerMapper {

    public static Collection<UserAnswerDto> toDto(Collection<UserAnswer> userAnswers) {

        if (userAnswers == null || userAnswers.isEmpty()) {
            throw new IllegalArgumentException("userAnswers is empty. Cannot do toDto mapping.");
        }

        return userAnswers.stream().map(ua -> new UserAnswerDto(
                ua.getQuestion().getId(),
                ua.getDescription(),
                ua.getCanonicalKey())
        ).collect(Collectors.toSet());
    }
}
