package com.ctorres.pokequiz.domain.model;

import com.ctorres.pokequiz.domain.model.auth.User;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Quiz {
    private final UUID id;
    private final Instant startDate;
    private final Instant endDate;
    private QuizState state;
    private final DifficultLevel difficultLevel;
    private final User user;
    private final Set<Question> questions;

    public Quiz(
            UUID id,
            Instant startDate,
            Instant endDate,
            QuizState state,
            DifficultLevel difficultLevel,
            User user,
            Set<Question> questions) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevel = difficultLevel;
        this.user = user;
        this.questions = questions;
    }

    public static Quiz create(
            QuizState state,
            DifficultLevel difficultLevel,
            User user
    ) {
        return new Quiz(
                UUID.randomUUID(),
                Instant.now(),
                null,
                Objects.requireNonNull(state),
                Objects.requireNonNull(difficultLevel),
                Objects.requireNonNull(user),
                new HashSet<>()
        );
    }

    public static Quiz restore(
            UUID id,
            Instant startDate,
            Instant endDate,
            QuizState state,
            DifficultLevel difficultLevel,
            User user,
            Set<Question> questions
    ) {
        return new Quiz(
                id,
                startDate,
                endDate,
                state,
                difficultLevel,
                user,
                questions
        );
    }

    public UUID getId() {
        return id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public QuizState getState() {
        return state;
    }

    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
    }

    public User getUser() {
        return user;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setState(QuizState state) {
        this.state = state;
    }
}