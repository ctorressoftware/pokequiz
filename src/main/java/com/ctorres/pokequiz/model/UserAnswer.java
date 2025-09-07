package com.ctorres.pokequiz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String canonicalKey;

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private boolean active;

    @OneToOne(targetEntity = Question.class)
    private Question question;

    public UserAnswer() {
    }

    public UserAnswer(Long id, String description, String canonicalKey, boolean correct, boolean active,
            Question question) {
        this.id = id;
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.correct = correct;
        this.active = active;
        this.question = question;
    }

    public UserAnswer(String description, String canonicalKey, boolean correct, boolean active, Question question) {
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.correct = correct;
        this.active = active;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", canonicalKey='" + canonicalKey + '\'' +
                ", correct=" + correct +
                ", active=" + active +
                ", question=" + (question != null ? question.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserAnswer))
            return false;
        UserAnswer that = (UserAnswer) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
