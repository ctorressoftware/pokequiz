package com.ctorres.pokequiz.application.usecase.quiz.model;

import java.util.UUID;

public record SubmittedUserAnswer(
        UUID questionId,
        String description,
        String value
) {}