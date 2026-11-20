package com.ctorres.pokequiz.application.usecase.quiz.model;

import java.util.Map;
import java.util.UUID;

public record EvaluationResult(Map<UUID, Boolean> result, double score) {

    public static EvaluationResult of(Map<UUID, Boolean> result, double score) {
        return new EvaluationResult(result, score);
    }
}

