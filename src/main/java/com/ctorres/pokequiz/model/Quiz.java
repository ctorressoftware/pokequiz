package com.ctorres.pokequiz.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate initialDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(targetEntity = State.class)
    private State state;

    @ManyToOne(targetEntity = DifficultLevel.class)
    private DifficultLevel difficultLevel;

    @ManyToOne(targetEntity = User.class)
    private User user;
}
