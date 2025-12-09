package com.ctorres.pokequiz.enums;

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