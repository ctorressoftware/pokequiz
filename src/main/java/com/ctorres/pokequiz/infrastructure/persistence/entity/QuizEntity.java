package com.ctorres.pokequiz.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class QuizEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private Instant initialDate;

    private Instant endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private StateEntity stateEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "difficult_level_id", nullable = false)
    private DifficultLevelEntity difficultLevelEntity;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private final Set<QuestionEntity> questionEntities = new HashSet<>();

    public QuizEntity() {}

    public QuizEntity(Instant initialDate, Instant endDate, StateEntity stateEntity, DifficultLevelEntity difficultLevelEntity, UserEntity userEntity) {
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.stateEntity = stateEntity;
        this.difficultLevelEntity = difficultLevelEntity;
        this.userEntity = userEntity;
    }

    public QuizEntity(UUID id, Instant initialDate, Instant endDate, StateEntity stateEntity, DifficultLevelEntity difficultLevelEntity, UserEntity userEntity) {
        this.id = id;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.stateEntity = stateEntity;
        this.difficultLevelEntity = difficultLevelEntity;
        this.userEntity = userEntity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public StateEntity getState() {
        return stateEntity;
    }

    public void setState(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
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

    public Set<QuestionEntity> getQuestions() {
        return questionEntities;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof QuizEntity quizEntity))
            return false;
        return id != null && id.equals(quizEntity.id);
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
        private StateEntity stateEntity;
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

        public Builder state(StateEntity stateEntity) {
            this.stateEntity = stateEntity;
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

        public QuizEntity build() {
            return new QuizEntity(
                    initialDate,
                    endDate,
                    stateEntity,
                    difficultLevelEntity,
                    userEntity
            );
        }
    }
}
