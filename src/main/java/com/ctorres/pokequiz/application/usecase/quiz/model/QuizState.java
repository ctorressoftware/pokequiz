package com.ctorres.pokequiz.application.usecase.quiz.model;

import java.util.UUID;

public enum QuizState {
    CREATED(UUID.fromString("1fc99a3f-aeba-442b-ae12-6f2a6a82308e"), "CREATED"),
    GENERATING(UUID.fromString("c3deb037-55cc-4981-8d3d-cefd57b9ac93"), "GENERATING"),
    READY(UUID.fromString("0e633055-4c24-4881-a47e-f41d9fd1358c"), "READY"),
    IN_PROGRESS(UUID.fromString("1d8f8fa8-af1a-444e-95b1-87922a544a57"), "IN_PROGRESS"),
    COMPLETED(UUID.fromString("a9454c45-ba1c-4101-b696-56d23fcb5c10"), "COMPLETED"),
    GENERATING_ERROR(UUID.fromString("11424db6-cb75-4d12-b1ac-e8ecfbf57187"), "GENERATING_ERROR");

    private final UUID id;
    private final String code;

    QuizState(UUID id, String code) {
        this.id = id;
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}