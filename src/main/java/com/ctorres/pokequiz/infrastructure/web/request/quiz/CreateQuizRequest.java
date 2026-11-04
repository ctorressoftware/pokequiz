package com.ctorres.pokequiz.infrastructure.web.request.quiz;

import java.util.UUID;

public class CreateQuizRequest {

    private final UUID difficultLevelId;

    public CreateQuizRequest(UUID difficultLevelId) {
        this.difficultLevelId = difficultLevelId;
    }

    public UUID getDifficultLevelId() {
        return difficultLevelId;
    }
}
