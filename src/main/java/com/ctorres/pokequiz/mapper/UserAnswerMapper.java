package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.request.UserAnswerDto;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.UserAnswer;
import java.util.Collection;
import java.util.Map;

public class UserAnswerMapper {

    public static Collection<UserAnswer> toDomain(Collection<UserAnswerDto> userAnswerDtos, Map<Long, Question> questionsById) {

        return userAnswerDtos.stream()
                .map(u -> new UserAnswer(
                        u.getDescription(),
                        u.getValue(),
                        true,
                        questionsById.get(u.getQuestionId()))
                ).toList();
    }
}
