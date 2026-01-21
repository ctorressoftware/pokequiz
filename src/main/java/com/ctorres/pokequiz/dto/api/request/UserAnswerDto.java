package com.ctorres.pokequiz.dto.api.request;

import com.ctorres.pokequiz.entity.Question;

public final class UserAnswerDto {
    private final String description;
    private final String canonicalKey;
    private final boolean active;
    private final Question question;

    public UserAnswerDto(
            String description,
            String canonicalKey,
            boolean active,
            Question question) {
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.active = active;
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public String getCanonicalKey() {
        return canonicalKey;
    }

    public boolean isActive() {
        return active;
    }

    public Question getQuestion() {
        return question;
    }
}
