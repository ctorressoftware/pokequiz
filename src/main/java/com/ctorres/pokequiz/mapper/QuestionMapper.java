package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.response.QuestionDto;
import com.ctorres.pokequiz.entity.Question;
import java.util.HashSet;
import java.util.Set;

public class QuestionMapper {

    public static Set<QuestionDto> toDto(Set<Question> questions) {

        var questionDtoSet = new HashSet<QuestionDto>();

        questions.forEach(question -> questionDtoSet.add(
                new QuestionDto(
                        question.getDescription(),
                        AnswerMapper.toDto(question.getAnswers())
                )
        ));

        return questionDtoSet;
    }
}
