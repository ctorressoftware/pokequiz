package com.ctorres.pokequiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String canonicalKey;

    @Column(nullable = false)
    private boolean active;

    @OneToOne(targetEntity = Question.class)
    private Question question;

    // Constructor vacío (requerido por JPA)
    public Answer() {
    }

    // Constructor con todos los campos
    public Answer(Long id, String description, String canonicalKey, boolean active, Question question) {
        this.id = id;
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.active = active;
        this.question = question;
    }

    // Constructor sin id (útil para crear nuevas entidades)
    public Answer(String description, String canonicalKey, boolean active, Question question) {
        this.description = description;
        this.canonicalKey = canonicalKey;
        this.active = active;
        this.question = question;
    }

    // Getters y Setters
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
        return "Answer{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", canonicalKey='" + canonicalKey + '\'' +
                ", active=" + active +
                ", question=" + (question != null ? question.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Answer))
            return false;
        Answer answer = (Answer) o;
        return id != null && id.equals(answer.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
