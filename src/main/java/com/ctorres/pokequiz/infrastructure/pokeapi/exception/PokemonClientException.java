package com.ctorres.pokequiz.exception;

public class PokemonClientException extends RuntimeException {

    public PokemonClientException() {
        super("PokeAPI doesn't respond correctly.");
    }
    
    public PokemonClientException(String url) {
        super("PokeAPI doesn't respond for url: " + url);
    }
}
