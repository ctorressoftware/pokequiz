package com.ctorres.pokequiz.dto.api.response;

import com.ctorres.pokequiz.dto.api.UserAnswerDto;
import java.util.Collection;

public class QuestionDto {
    private final Long id;
    private final String description;
    private final String frontImageUrl;
    private final String backImageUrl;
    private final Collection<AnswerDto> answers;
    private final Collection<UserAnswerDto> userAnswers;

    public QuestionDto(
            Long id,
            String description,
            String frontImageUrl,
            String backImageUrl,
            Collection<AnswerDto> answers,
            Collection<UserAnswerDto> userAnswers) {
        this.id = id;
        this.description = description;
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.answers = answers;
        this.userAnswers = userAnswers;
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
    public Collection<UserAnswerDto> getUserAnswers() {
        return userAnswers;
    }
}

