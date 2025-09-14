package com.ctorres.pokequiz.dto.api.generator;

import java.util.Objects;

public class GeneratedAnswer {
    private String description;
    private boolean correct;

    public GeneratedAnswer() {
    }

    public GeneratedAnswer(String description, boolean correct) {
        this.description = description;
        this.correct = correct;
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
                ", description='" + description + "\'" +
                ", correct='" + correct + "'\'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GeneratedQuestion))
            return false;
        GeneratedAnswer answer = (GeneratedAnswer) o;
        return description != null && description.equals(answer.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
