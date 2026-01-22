package com.ctorres.pokequiz.dto.api.response;

import java.util.Collection;

public class QuestionDto {
    private final Long id;
    private final String description;
    private final String frontImageUrl;
    private final String backImageUrl;
    private final Collection<AnswerDto> answers;

    public QuestionDto(Long id, String description, String frontImageUrl, String backImageUrl, Collection<AnswerDto> answers) {
        this.id = id;
        this.description = description;
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.answers = answers;
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
}

