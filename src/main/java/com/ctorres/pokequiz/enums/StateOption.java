package com.ctorres.pokequiz.enums;

// TODO define deeply the quiz states and update state data with Liquibase
public enum StateOption {
    CREATED(1L),
    COMPLETED(2L);

    private final Long id;

    StateOption(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}