package com.ctorres.pokequiz.entity;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant initialDate;

    @Column(nullable = false)
    private Instant endDate;

    @ManyToOne(targetEntity = State.class)
    private State state;

    @ManyToOne(targetEntity = DifficultLevel.class)
    private DifficultLevel difficultLevel;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Quiz() {}

    public Quiz(Long id, Instant initialDate, Instant endDate, State state, DifficultLevel difficultLevel,
            User user) {
        this.id = id;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevel = difficultLevel;
        this.user = user;
    }

    public Quiz(Instant initialDate, Instant endDate, State state, DifficultLevel difficultLevel, User user) {
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevel = difficultLevel;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Instant initialDate) {
        this.initialDate = initialDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(DifficultLevel difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", initialDate=" + initialDate +
                ", endDate=" + endDate +
                ", state=" + (state != null ? state.getId() : null) +
                ", difficultLevel=" + (difficultLevel != null ? difficultLevel.getId() : null) +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Quiz))
            return false;
        Quiz quiz = (Quiz) o;
        return id != null && id.equals(quiz.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant initialDate;
        private Instant endDate;
        private State state;
        private DifficultLevel difficultLevel;
        private User user;

        private Builder() {}

        public Builder initialDate(Instant initialDate) {
            this.initialDate = initialDate;
            return this;
        }

        public Builder endDate(Instant endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder state(State state) {
            this.state = state;
            return this;
        }

        public Builder difficultLevel(DifficultLevel difficultLevel) {
            this.difficultLevel = difficultLevel;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Quiz build() {
            return new Quiz(
                    initialDate,
                    endDate,
                    state,
                    difficultLevel,
                    user
            );
        }
    }
}
