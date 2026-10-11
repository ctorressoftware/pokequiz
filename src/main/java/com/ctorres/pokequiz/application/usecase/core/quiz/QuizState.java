package com.ctorres.pokequiz.enums;

public enum QuizState {
    CREATED(1L, "CREATED"),
    GENERATING(2L, "GENERATING"),
    READY(3L, "READY"),
    IN_PROGRESS(4L, "IN_PROGRESS"),
    COMPLETED(5L, "COMPLETED"),
    GENERATING_ERROR(10L, "GENERATING_ERROR");

    private final Long id;
    private final String code;

    QuizState(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}