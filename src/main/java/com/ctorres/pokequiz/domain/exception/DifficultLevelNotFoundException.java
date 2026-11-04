package com.ctorres.pokequiz.domain.exception;

import java.util.UUID;

public class DifficultLevelNotFoundException extends RuntimeException {

    public DifficultLevelNotFoundException(UUID id) {
        super("The difficult level with ID = " + id + " doesn't exists");
    }
}
