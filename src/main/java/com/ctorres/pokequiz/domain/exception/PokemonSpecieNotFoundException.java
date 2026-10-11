package com.ctorres.pokequiz.exception;

public class PokemonSpecieNotFound extends RuntimeException {

    public PokemonSpecieNotFound() {
        super("Cannot find any pokemon specie.");
    }

    public PokemonSpecieNotFound(String name) {
        super("Pokemon specie not found with name: " + name);
    } 
    
}
