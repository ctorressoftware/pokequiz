package com.ctorres.pokequiz.entity;

import java.util.Objects;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne(targetEntity = Quiz.class)
    private Quiz quiz;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private Set<Answer> answers;

    public Question() {}

    public Question(Long id, String description, boolean active, Quiz quiz) {
        this.id = id;
        this.description = description;
        this.active = active;
        this.quiz = quiz;
    }

    public Question(String description, boolean active, Quiz quiz) {
        this.description = description;
        this.active = active;
        this.quiz = quiz;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", quiz=" + (quiz != null ? quiz.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return id != null && id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
