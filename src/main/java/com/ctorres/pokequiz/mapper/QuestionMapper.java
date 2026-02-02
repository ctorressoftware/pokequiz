package com.ctorres.pokequiz.mapper;

import com.ctorres.pokequiz.dto.api.response.QuestionDto;
import com.ctorres.pokequiz.entity.Question;
import java.util.Collection;

public class QuestionMapper {

    public static Collection<QuestionDto> toDto(Collection<Question> questions) {

        return questions.stream()
                .map(q -> new QuestionDto(
                        q.getId(),
                        q.getDescription(),
                        q.getFrontImageUrl(),
                        q.getBackImageUrl(),
                        AnswerMapper.toDto(q.getAnswers()),
                       q.getUserAnswers().isEmpty() ? null : UserAnswerMapper.toDto(q.getUserAnswers())
                )).toList();
    }
}
