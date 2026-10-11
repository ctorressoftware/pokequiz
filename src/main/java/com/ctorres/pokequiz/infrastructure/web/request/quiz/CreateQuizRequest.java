package com.ctorres.pokequiz.infrastructure.web.request.quiz;

public class CreateQuizRequest {

    private final Long difficultLevelId;

    public CreateQuizRequest(Long difficultLevelId) {
        this.difficultLevelId = difficultLevelId;
    }

    public Long getDifficultLevelId() {
        return difficultLevelId;
    }
}
