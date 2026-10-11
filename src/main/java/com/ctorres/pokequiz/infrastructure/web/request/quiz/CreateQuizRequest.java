package com.ctorres.pokequiz.dto.api.request;

public class CreateQuizRequest {

    private final Long difficultLevelId;

    public CreateQuizRequest(Long difficultLevelId) {
        this.difficultLevelId = difficultLevelId;
    }

    public Long getDifficultLevelId() {
        return difficultLevelId;
    }
}
