package com.ctorres.pokequiz.dto.api.response;

import com.ctorres.pokequiz.dto.api.UserAnswerDto;
import java.util.Collection;

public class QuestionDto {
    private final Long id;
    private final String description;
    private final String frontImageUrl;
    private final String backImageUrl;
    private final Collection<AnswerDto> answers;
    private final UserAnswerDto userAnswer;

    public QuestionDto(
            Long id,
            String description,
            String frontImageUrl,
            String backImageUrl,
            Collection<AnswerDto> answers,
            UserAnswerDto userAnswer) {
        this.id = id;
        this.description = description;
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.answers = answers;
        this.userAnswer = userAnswer;
    }

    public Long getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getFrontImageUrl() {
        return frontImageUrl;
    }
    public String getBackImageUrl() {
        return backImageUrl;
    }
    public Collection<AnswerDto> getAnswers() {
        return answers;
    }
    public UserAnswerDto getUserAnswer() {
        return userAnswer;
    }
}

