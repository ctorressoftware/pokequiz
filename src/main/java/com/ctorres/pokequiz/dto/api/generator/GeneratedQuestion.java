package com.ctorres.pokequiz.dto.api.generator;

import java.util.Objects;

public class GeneratedQuestion {

    private String description;

    public GeneratedQuestion() {
    }

    public GeneratedQuestion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GeneratedQuestion{" +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GeneratedQuestion))
            return false;
        GeneratedQuestion question = (GeneratedQuestion) o;
        return description != null && description.equals(question.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
