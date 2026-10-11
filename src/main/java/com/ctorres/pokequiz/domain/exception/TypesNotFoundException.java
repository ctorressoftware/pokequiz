package com.ctorres.pokequiz.domain.exception;

public class TypesNotFoundException extends RuntimeException {
    
    public TypesNotFoundException() {
        super("Cannot find any type.");
    }

    public TypesNotFoundException(String name) {
        super("Type not found with name: " + name);
    }
}
