package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.UserAnswerDto;
import com.ctorres.pokequiz.dto.api.request.UserAnswerDtoRequest;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.entity.UserAnswer;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class UserAnswerMapper {

    public static Collection<UserAnswer> toDomain(Collection<UserAnswerDtoRequest> userAnswerDtos, Map<Long, Question> questionsById) {

        return userAnswerDtos.stream()
                .map(u -> new UserAnswer(
                        u.getDescription(),
                        u.getValue(),
                        true,
                        questionsById.get(u.getQuestionId()))
                ).toList();
    }

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
