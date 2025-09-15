package com.ctorres.pokequiz.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.ctorres.pokequiz.config.ApiConfig;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.util.PokeApiUtils;

@Component
public class PokeApiClient {

    final private RestTemplate restTemplate;
    final private PokeApiUtils utils;
    final private ApiConfig apiConfig;

    public PokeApiClient(RestTemplate restTemplate, PokeApiUtils utils, ApiConfig apiConfig) {
        this.restTemplate = restTemplate;
        this.utils = utils;
        this.apiConfig = apiConfig;
    }

    // public getAllPokemon() { /* TODO */}

    public Pokemon getPokemon(String name) {

        try {

            if (name == null) {
                throw new IllegalArgumentException("Must send a valid pokemon name.");
            }

            final String formattedName = name.toLowerCase().trim();

            String url = new StringBuilder()
                    .append(apiConfig.getBaseUrl())
                    .append(apiConfig.getEndpointGetPokemon())
                    .append("/")
                    .append(formattedName)
                    .toString();

            Pokemon pokemon = restTemplate.getForObject(url, Pokemon.class);

            return pokemon;

        } catch (RestClientException e) {
            throw new RuntimeException("Prueba");
        }

    }

    public Pokemon getPokemon(int pokemonNumber) {

        final int POKEDEX_MIN = apiConfig.getPokedexMinNumber();
        final int POKEDEX_MAX = apiConfig.getPokedexMaxNumber();

        if (pokemonNumber < POKEDEX_MIN || pokemonNumber > POKEDEX_MAX) {
            String errorMessage = String.format("Must send a valid pokedex number (%d-%d).", POKEDEX_MIN, POKEDEX_MAX);
            throw new RuntimeException(errorMessage);
        }

        String url = new StringBuilder()
                .append(apiConfig.getBaseUrl())
                .append(apiConfig.getEndpointGetPokemon())
                .append("/")
                .append(pokemonNumber)
                .toString();

        Pokemon pokemon = restTemplate.getForObject(url, Pokemon.class);

        return pokemon;
    }

    public Pokemon getRandomPokemon() {
        int pokemonNumber = utils.getRandomPokemonNumber();
        return getPokemon(pokemonNumber);
    }
}
