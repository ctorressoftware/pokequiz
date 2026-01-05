package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.response.AnswerDto;
import com.ctorres.pokequiz.entity.Answer;

import java.util.HashSet;
import java.util.Set;

public class AnswerMapper {

    public static Set<AnswerDto> toDto(Set<Answer> answers) {

        var answerDtoSet = new HashSet<AnswerDto>();

        answers.forEach(answer -> answerDtoSet.add(
                new AnswerDto(answer.getDescription())
        ));

        return answerDtoSet;
    }
}
