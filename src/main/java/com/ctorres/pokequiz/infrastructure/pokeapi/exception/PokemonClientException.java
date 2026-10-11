package com.ctorres.pokequiz.infrastructure.pokeapi.exception;

public class PokemonClientException extends RuntimeException {

    public PokemonClientException() {
        super("PokeAPI doesn't respond correctly.");
    }
    
    public PokemonClientException(String url) {
        super("PokeAPI doesn't respond for url: " + url);
    }
}
