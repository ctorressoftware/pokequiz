package com.ctorres.pokequiz.domain.model;

import java.util.Objects;
import java.util.UUID;

public class UserAnswer {
    private UUID id;
    private String description;
    private String canonicalKey;
    private boolean active;
    private Question question;

    public UserAnswer(
            UUID id,
            String description,
            String canonicalKey,
            boolean active,
            Question question
    ) {
        this.id = Objects.requireNonNull(id);
        this.description = Objects.requireNonNull(description);
        this.canonicalKey = Objects.requireNonNull(canonicalKey);
        this.active = active;
        this.question = Objects.requireNonNull(question);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCanonicalKey() {
        return canonicalKey;
    }

    public void setCanonicalKey(String canonicalKey) {
        this.canonicalKey = canonicalKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserAnswer that))
            return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
