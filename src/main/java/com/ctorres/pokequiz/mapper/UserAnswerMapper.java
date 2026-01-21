package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.request.UserAnswerDto;
import com.ctorres.pokequiz.dto.api.response.AnswerDto;
import com.ctorres.pokequiz.entity.Answer;
import com.ctorres.pokequiz.entity.UserAnswer;

import java.util.Collection;

public class UserAnswerMapper {

    public static Collection<UserAnswer> toDomain(Collection<UserAnswerDto> userAnswerDtos) {

        return userAnswerDtos.stream()
                .map(u -> new UserAnswer(
                        u.getDescription(),
                        u.getCanonicalKey(),
                        true,
                        u.getQuestion())
                ).toList();
    }
}
