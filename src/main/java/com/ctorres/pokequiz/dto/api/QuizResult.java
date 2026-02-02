package com.ctorres.pokequiz.dto.api;

import java.util.Map;

public class QuizResult {
    private final Map<Long, Boolean> result;
    private final double score;

    private QuizResult(Map<Long, Boolean> result, double score) {
        this.result = result;
        this.score = score;
    }

    public static QuizResult of(Map<Long, Boolean> result, double score) {
        return new QuizResult(result, score);
    }

    // TODO: Change this to a Object List
    public Map<Long, Boolean> getResult() {
        return result;
    }

    public double getScore() {
        return score;
    }
}
