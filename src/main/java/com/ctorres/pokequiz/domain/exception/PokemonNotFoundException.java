package com.ctorres.pokequiz.exception;

public class PokemonNotFoundException extends RuntimeException {

    public PokemonNotFoundException() {
        super("Cannot find any Pokemon.");
    }

    public PokemonNotFoundException(String name) {
        super("Pokemon not found with name: " + name);
    } 
}
