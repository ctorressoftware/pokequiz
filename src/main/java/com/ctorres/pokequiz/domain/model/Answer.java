package com.ctorres.pokequiz.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Answer {
    private UUID id;
    private String description;
    private String canonicalKey;
    private boolean correct;
    private boolean active;
    private Question question;

    public Answer(
            UUID id,
            String description,
            String canonicalKey,
            boolean correct,
            boolean active,
            Question question) {
        this.id = id;
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.correct = correct;
        this.active = active;
        this.question = question;
    }

    public static Answer restore(
            UUID id,
            String description,
            String canonicalKey,
            boolean correct,
            boolean active,
            Question question) {

        return new Answer(
                Objects.requireNonNull(id),
                Objects.requireNonNull(description),
                Objects.requireNonNull(canonicalKey),
                correct,
                active,
                Objects.requireNonNull(question)
        );
    }

    public static Answer create(
            String description,
            String canonicalKey,
            boolean correct,
            boolean active,
            Question question) {

        return new Answer(
                UUID.randomUUID(),
                Objects.requireNonNull(description),
                Objects.requireNonNull(canonicalKey),
                correct,
                active,
                Objects.requireNonNull(question)
        );
    }

    public UUID getId() {
        return id;
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

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
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
        if (!(o instanceof Answer answer))
            return false;
        return id != null && id.equals(answer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
