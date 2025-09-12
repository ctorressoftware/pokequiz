package com.ctorres.pokequiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.util.Constants;;

@RestController
@RequestMapping(Constants.POKEMON)
public class PokequizController {

    final private PokeApiClient pokeApiClient;

    public PokequizController(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }
    
    @GetMapping(Constants.GET_POKEMON)
    public Pokemon getPokemonByName() {
        Pokemon pokemon = pokeApiClient.getPokemon("Piku");
        return pokemon;
    }
    
    @GetMapping("/pokeid")
    public Pokemon getPokemonByNumber() {
        Pokemon pokemon = pokeApiClient.getPokemon(1);
        return pokemon;
    }

}
