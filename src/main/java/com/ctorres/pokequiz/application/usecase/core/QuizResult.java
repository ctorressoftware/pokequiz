package com.ctorres.pokequiz.application.usecase.core;

import java.util.Map;
import java.util.UUID;

public class QuizResult {
    private final Map<UUID, Boolean> result;
    private final double score;

    private QuizResult(Map<UUID, Boolean> result, double score) {
        this.result = result;
        this.score = score;
    }

    public static QuizResult of(Map<UUID, Boolean> result, double score) {
        return new QuizResult(result, score);
    }

    // TODO: Change this to a Object List
    public Map<UUID, Boolean> getResult() {
        return result;
    }

    public double getScore() {
        return score;
    }
}
