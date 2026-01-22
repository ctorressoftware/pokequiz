package com.ctorres.pokequiz.dto.api.response;

import com.ctorres.pokequiz.entity.UserAnswer;

import java.util.Map;

public class EvaluateAnswersResponse {
    private final Map<UserAnswer, Boolean> result;
    private final int score;

    public EvaluateAnswersResponse(Map<UserAnswer, Boolean> result, int score) {
        this.result = result;
        this.score = score;
    }

    public Map<UserAnswer, Boolean> getResult() {
        return result;
    }

    public int getScore() {
        return score;
    }
}
