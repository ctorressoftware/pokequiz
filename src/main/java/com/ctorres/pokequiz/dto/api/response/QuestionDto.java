package com.ctorres.pokequiz.dto.api.response;

import java.util.Set;

public class QuestionDto {
    private final String description;
    private final Set<AnswerDto> answers;

    public QuestionDto(String description, Set<AnswerDto> answers) {
        this.description = description;
        this.answers = answers;
    }

    public String getDescription() {
        return description;
    }

    public Set<AnswerDto> getAnswers() {
        return answers;
    }
}

