package com.ctorres.pokequiz.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant initialDate;

    private Instant endDate;

    // @ManyToOne(targetEntity = State.class)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    // @ManyToOne(targetEntity = DifficultLevel.class)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "difficult_level_id", nullable = false)
    private DifficultLevelEntity difficultLevelEntity;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private final Set<Question> questions = new HashSet<>();

    public Quiz() {}

    public Quiz(Instant initialDate, Instant endDate, State state, DifficultLevelEntity difficultLevelEntity, UserEntity userEntity) {
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevelEntity = difficultLevelEntity;
        this.userEntity = userEntity;
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

    public DifficultLevelEntity getDifficultLevel() {
        return difficultLevelEntity;
    }

    public void setDifficultLevel(DifficultLevelEntity difficultLevelEntity) {
        this.difficultLevelEntity = difficultLevelEntity;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
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
        return getClass().hashCode();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant initialDate;
        private Instant endDate;
        private State state;
        private DifficultLevelEntity difficultLevelEntity;
        private UserEntity userEntity;

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

        public Builder difficultLevel(DifficultLevelEntity difficultLevelEntity) {
            this.difficultLevelEntity = difficultLevelEntity;
            return this;
        }

        public Builder user(UserEntity userEntity) {
            this.userEntity = userEntity;
            return this;
        }

        public Quiz build() {
            return new Quiz(
                    initialDate,
                    endDate,
                    state,
                    difficultLevelEntity,
                    userEntity
            );
        }
    }
}
