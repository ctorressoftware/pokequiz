package com.ctorres.pokequiz.application.usecase.core.quiz.evaluation;

import java.util.Map;
import java.util.UUID;

public class EvaluationResult {
    private final Map<UUID, Boolean> result;
    private final double score;

    private EvaluationResult(Map<UUID, Boolean> result, double score) {
        this.result = result;
        this.score = score;
    }

    public static EvaluationResult of(Map<UUID, Boolean> result, double score) {
        return new EvaluationResult(result, score);
    }

    public Map<UUID, Boolean> getResult() {
        return result;
    }

    public double getScore() {
        return score;
    }
}

