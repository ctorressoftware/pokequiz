package com.ctorres.pokequiz.application.usecase.core.quiz;

import java.util.UUID;

/* TODO: It's important to recreate this or delete it. Auto-increment ID is removed.
*  For that, maybe it will be necessary to re-insert rows with static UUIDs.
* */
public enum QuizState {
    CREATED(UUID.randomUUID(), "CREATED"),
    GENERATING(UUID.randomUUID(), "GENERATING"),
    READY(UUID.randomUUID(), "READY"),
    IN_PROGRESS(UUID.randomUUID(), "IN_PROGRESS"),
    COMPLETED(UUID.randomUUID(), "COMPLETED"),
    GENERATING_ERROR(UUID.randomUUID(), "GENERATING_ERROR");

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