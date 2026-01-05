package com.ctorres.pokequiz.dto.api.response;

public class AnswerDto {

    private final String description;

    public AnswerDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}