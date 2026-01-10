package com.ctorres.pokequiz.entity;

import java.util.*;
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

    @Column(nullable = true, name = "front_image_url")
    private String frontImageUrl;

    @Column(nullable = true, name = "back_image_url")
    private String backImageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "question",
            fetch = FetchType.LAZY,
            cascade=CascadeType.ALL,
            orphanRemoval=true)
    private final Set<Answer> answers = new HashSet<>();

    protected Question() {}

    public Question(String description, boolean active, String frontImageUrl, String backImageUrl, Quiz quiz, Set<Answer> answers) {
        this.description = description;
        this.active = active;
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.quiz = quiz;
        this.setAnswers(answers);
    }

    public Long getId() {
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

    public Set<Answer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }

    private void setAnswers(Set<Answer> newAnswers) {
        this.answers.clear();

        if (newAnswers == null || newAnswers.isEmpty()) {
            return;
        }

        for (Answer a : newAnswers) {
            a.setQuestion(this);
            this.answers.add(a);
        }
    }

    public Quiz getQuiz() {
        return quiz;
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
