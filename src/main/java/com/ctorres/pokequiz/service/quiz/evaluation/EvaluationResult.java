package com.ctorres.pokequiz.service.quiz.evaluation;

import java.util.Map;

public class EvaluationResult {
    private final Map<Long, Boolean> result;
    private final double score;

    private EvaluationResult(Map<Long, Boolean> result, double score) {
        this.result = result;
        this.score = score;
    }

    public static EvaluationResult of(Map<Long, Boolean> result, double score) {
        return new EvaluationResult(result, score);
    }

    public Map<Long, Boolean> getResult() {
        return result;
    }

    public double getScore() {
        return score;
    }
}

