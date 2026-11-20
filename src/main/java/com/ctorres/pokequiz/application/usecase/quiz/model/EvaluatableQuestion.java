package com.ctorres.pokequiz.application.usecase.quiz.model;

import java.util.UUID;

public record EvaluatableQuestion(
        UUID questionId,
        String correctAnswer,
        String userAnswer
) {}
