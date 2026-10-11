package com.ctorres.pokequiz.dto.api.response;

public class AnswerDto {

    private final String description;
    private final String value;

    public AnswerDto(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }
}