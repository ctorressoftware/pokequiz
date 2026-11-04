package com.ctorres.pokequiz.application.usecase.core.mapper;

import com.ctorres.pokequiz.infrastructure.web.response.quiz.UserAnswerDto;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswerEntity;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserAnswerMapper {

    public static Collection<UserAnswerDto> toDto(Collection<UserAnswerEntity> userAnswerEntities) {

        if (userAnswerEntities == null || userAnswerEntities.isEmpty()) {
            throw new IllegalArgumentException("userAnswers is empty. Cannot do toDto mapping.");
        }

        return userAnswerEntities.stream().map(ua -> new UserAnswerDto(
                ua.getQuestion().getId(),
                ua.getDescription(),
                ua.getCanonicalKey())
        ).collect(Collectors.toSet());
    }
}
