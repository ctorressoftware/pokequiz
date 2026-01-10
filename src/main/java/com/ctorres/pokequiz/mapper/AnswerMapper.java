package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.response.AnswerDto;
import com.ctorres.pokequiz.entity.Answer;

import java.util.Collection;

public class AnswerMapper {

    public static Collection<AnswerDto> toDto(Collection<Answer> answers) {

        return answers.stream()
                .map(a -> new AnswerDto(a.getDescription()))
                .toList();
    }
}
