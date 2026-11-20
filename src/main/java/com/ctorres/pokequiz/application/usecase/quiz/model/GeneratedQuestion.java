package com.ctorres.pokequiz.application.usecase.quiz.model;

public record GeneratedQuestion(
        String description,
        String pokemonFrontImage,
        String pokemonBackImage
) {
    public GeneratedQuestion(String description) {
        this(description, null, null);
    }
}
