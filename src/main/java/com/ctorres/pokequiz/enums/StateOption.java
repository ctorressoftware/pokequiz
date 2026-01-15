package com.ctorres.pokequiz.enums;

public enum StateOption {
    CREATED(1L),
    GENERATING(2L),
    GENERATED(3L),
    COMPLETED(4L);

    private final Long id;

    StateOption(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}