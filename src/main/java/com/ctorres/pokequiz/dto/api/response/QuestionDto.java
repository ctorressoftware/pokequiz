package com.ctorres.pokequiz.dto.api.response;

import java.util.Collection;

public class QuestionDto {
    private final String description;
    private final Collection<AnswerDto> answers;

    public QuestionDto(String description, Collection<AnswerDto> answers) {
        this.description = description;
        this.answers = answers;
    }

    public String getDescription() {
        return description;
    }

    public Collection<AnswerDto> getAnswers() {
        return answers;
    }
}

