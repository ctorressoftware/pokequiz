package com.ctorres.pokequiz.enums;

/* TODO add: define deeply the quiz states and update state data
    with Liquibase, quit auto-incremental ids and add new column code */
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