package com.ctorres.pokequiz.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class UserAnswerEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String canonicalKey;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity questionEntity;

    public UserAnswerEntity() {}

    public UserAnswerEntity(String description, String canonicalKey, boolean active, QuestionEntity questionEntity) {
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.active = active;
        this.questionEntity = questionEntity;
    }

    public UserAnswerEntity(UUID id, String description, String canonicalKey, boolean active, QuestionEntity questionEntity) {
        this.id = id;
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.active = active;
        this.questionEntity = questionEntity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public QuestionEntity getQuestion() {
        return questionEntity;
    }

    public void setQuestion(QuestionEntity questionEntity) {
        this.questionEntity = questionEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserAnswerEntity that))
            return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
