package com.ctorres.pokequiz.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "answer")
public class AnswerEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String canonicalKey;

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    public AnswerEntity() {}

    public AnswerEntity(UUID id, String description, String canonicalKey, boolean correct, boolean active, QuestionEntity question) {
        this.id = id;
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.correct = correct;
        this.active = active;
        this.question = question;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCanonicalKey() {
        return canonicalKey;
    }

    public void setCanonicalKey(String canonicalKey) {
        this.canonicalKey = canonicalKey;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", canonicalKey='" + canonicalKey + '\'' +
                ", correct='" + correct + '\'' +
                ", active=" + active +
                ", question=" + (question != null ? question.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AnswerEntity answerEntity))
            return false;
        return id != null && id.equals(answerEntity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
