package com.ctorres.pokequiz.domain.model;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record Question(
        UUID id,
        String description,
        boolean active,
        String frontImageUrl,
        String backImageUrl,
        Quiz quiz,
        Set<Answer> answers,
        Set<UserAnswer> userAnswers
) {

    public static Question create(
            String description,
            boolean active,
            String frontImageUrl,
            String backImageUrl,
            Quiz quiz,
            Set<Answer> answers,
            Set<UserAnswer> userAnswers
    ) {
        return new Question(
                UUID.randomUUID(),
                Objects.requireNonNull(description),
                active,
                frontImageUrl,
                backImageUrl,
                Objects.requireNonNull(quiz),
                Objects.requireNonNull(answers),
                Objects.requireNonNull(userAnswers)
        );
    }

    public static Question restore(
            UUID id,
            String description,
            boolean active,
            String frontImageUrl,
            String backImageUrl,
            Quiz quiz,
            Set<Answer> answers,
            Set<UserAnswer> userAnswers
    ) {
        return new Question(
                id,
                description,
                active,
                frontImageUrl,
                backImageUrl,
                quiz,
                answers,
                userAnswers
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        return other instanceof Question question && id != null && id.equals(question.id());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setUserAnswers(Set<UserAnswer> newUserAnswers) {
        this.userAnswers.clear();

        if (newUserAnswers == null || newUserAnswers.isEmpty()) {
            return;
        }

        for (UserAnswer ua : newUserAnswers) {
            ua.setQuestion(this);
            this.userAnswers.add(ua);
        }
    }

    public void setSingleUserAnswer(UserAnswer ua) {
        if (ua == null) throw new IllegalArgumentException("UserAnswer cannot be null");
        setUserAnswers(Set.of(ua));
    }
}
