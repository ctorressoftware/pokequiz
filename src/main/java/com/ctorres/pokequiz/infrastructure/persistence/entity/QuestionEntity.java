package com.ctorres.pokequiz.infrastructure.persistence.entity;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class QuestionEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = true, name = "front_image_url")
    private String frontImageUrl;

    @Column(nullable = true, name = "back_image_url")
    private String backImageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quizEntity;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final Set<AnswerEntity> answerEntities = new HashSet<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private final Set<UserAnswerEntity> userAnswerEntities = new HashSet<>();

    protected QuestionEntity() {
    }

    public QuestionEntity(
            String description,
            boolean active,
            String frontImageUrl,
            String backImageUrl,
            QuizEntity quizEntity,
            Set<AnswerEntity> answerEntities,
            Set<UserAnswerEntity> userAnswerEntities) {
        this.description = description;
        this.active = active;
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.quizEntity = quizEntity;
        this.setAnswers(answerEntities);
        this.setUserAnswers(userAnswerEntities);
    }

    public QuestionEntity(
            UUID id,
            String description,
            boolean active,
            String frontImageUrl,
            String backImageUrl,
            QuizEntity quizEntity,
            Set<AnswerEntity> answerEntities,
            Set<UserAnswerEntity> userAnswerEntities) {
        this.id = id;
        this.description = description;
        this.active = active;
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.quizEntity = quizEntity;
        this.setAnswers(answerEntities);
        this.setUserAnswers(userAnswerEntities);
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public String getFrontImageUrl() {
        return frontImageUrl;
    }

    public String getBackImageUrl() {
        return backImageUrl;
    }

    public Set<AnswerEntity> getAnswers() {
        return Collections.unmodifiableSet(answerEntities);
    }

    public Set<UserAnswerEntity> getUserAnswers() {
        return userAnswerEntities;
    }

    private void setAnswers(Set<AnswerEntity> newAnswerEntities) {
        this.answerEntities.clear();

        if (newAnswerEntities == null || newAnswerEntities.isEmpty()) {
            return;
        }

        for (AnswerEntity a : newAnswerEntities) {
            a.setQuestion(this);
            this.answerEntities.add(a);
        }
    }

    public void setUserAnswers(Set<UserAnswerEntity> newUserAnswerEntities) {
        this.userAnswerEntities.clear();

        if (newUserAnswerEntities == null || newUserAnswerEntities.isEmpty()) {
            return;
        }

        for (UserAnswerEntity ua : newUserAnswerEntities) {
            ua.setQuestion(this);
            this.userAnswerEntities.add(ua);
        }
    }

    public void setSingleUserAnswer(UserAnswerEntity ua) {
        if (ua == null) throw new IllegalArgumentException("UserAnswer cannot be null");
        setUserAnswers(Set.of(ua));
    }

    public QuizEntity getQuiz() {
        return quizEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionEntity questionEntity)) return false;
        return id != null && id.equals(questionEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
