package com.ctorres.pokequiz.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

@Entity(name = "quiz")
public class QuizEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private Instant initialDate;

    private Instant endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    private StateEntity state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "difficult_level_id", nullable = false)
    private DifficultLevelEntity difficultLevel;

    @ManyToOne(targetEntity = UserEntity.class, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private final Set<QuestionEntity> questionEntities = new HashSet<>();

    public QuizEntity() {}

    public QuizEntity(Instant initialDate, Instant endDate, StateEntity state, DifficultLevelEntity difficultLevel, UserEntity user) {
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevel = difficultLevel;
        this.user = user;
    }

    public QuizEntity(UUID id, Instant initialDate, Instant endDate, StateEntity state, DifficultLevelEntity difficultLevel, UserEntity user) {
        this.id = id;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevel = difficultLevel;
        this.user = user;
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
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public DifficultLevelEntity getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(DifficultLevelEntity difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public UserEntity getUser() {
        return user;
    }

    public Set<QuestionEntity> getQuestions() {
        return questionEntities;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof QuizEntity quiz))
            return false;
        return id != null && id.equals(quiz.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
