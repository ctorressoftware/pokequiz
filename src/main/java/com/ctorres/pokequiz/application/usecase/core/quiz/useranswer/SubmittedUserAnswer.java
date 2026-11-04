package com.ctorres.pokequiz.application.usecase.core.quiz.useranswer;

import java.util.UUID;

public record SubmittedUserAnswer(
        UUID questionId,
        String description,
        String value
) {}