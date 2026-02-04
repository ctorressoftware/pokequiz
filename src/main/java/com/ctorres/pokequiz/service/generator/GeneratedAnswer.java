package com.ctorres.pokequiz.service.generator;

import java.util.Objects;

public class GeneratedAnswer {

    private String canonicalKey;
    private String description;
    private boolean correct;

    public GeneratedAnswer() {
    }

    public GeneratedAnswer(String canonicalKey, String description, boolean correct) {
        this.canonicalKey = canonicalKey;
        this.description = description;
        this.correct = correct;
    }

    public String getCanonicalKey() {
        return canonicalKey;
    }

    public void setCanonicalKey(String canonicalKey) {
        this.canonicalKey = canonicalKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "GeneratedAnswer{" +
                "canonicalKey='" + canonicalKey + '\'' +
                ", description='" + description + '\'' +
                ", correct=" + correct +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GeneratedAnswer))
            return false;
        GeneratedAnswer that = (GeneratedAnswer) o;
        return correct == that.correct &&
                Objects.equals(canonicalKey, that.canonicalKey) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canonicalKey, description, correct);
    }
}
