package com.ctorres.pokequiz.domain.exception;

public class PokemonSpecieNotFoundException extends RuntimeException {

    public PokemonSpecieNotFoundException() {
        super("Cannot find any pokemon specie.");
    }

    public PokemonSpecieNotFoundException(String name) {
        super("Pokemon specie not found with name: " + name);
    } 
    
}
