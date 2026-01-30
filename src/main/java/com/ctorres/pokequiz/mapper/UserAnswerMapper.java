package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.UserAnswerDto;
import com.ctorres.pokequiz.dto.api.request.UserAnswerDtoRequest;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.UserAnswer;
import java.util.Collection;
import java.util.Map;

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

    public static UserAnswerDto toDto(UserAnswer userAnswer) {

        if (userAnswer == null) return null;

        return new UserAnswerDto(
                userAnswer.getQuestion().getId(),
                userAnswer.getDescription(),
                userAnswer.getCanonicalKey()
        );
    }
}
