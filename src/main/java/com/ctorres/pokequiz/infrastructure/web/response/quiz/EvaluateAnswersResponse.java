package com.ctorres.pokequiz.dto.api.response;

import java.util.Map;

public class EvaluateAnswersResponse {
    private final Map<Long, Boolean> result;
    private final int score;

    public EvaluateAnswersResponse(Map<Long, Boolean> result, int score) {
        this.result = result;
        this.score = score;
    }

    public Map<Long, Boolean> getResult() {
        return result;
    }

    public int getScore() {
        return score;
    }
}
