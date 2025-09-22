package com.ctorres.pokequiz.client;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.ctorres.pokequiz.config.ApiConfig;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.dto.pokeapi.PokemonSpecie;
import com.ctorres.pokequiz.dto.pokeapi.Type;
import com.ctorres.pokequiz.exception.PokemonClientException;
import com.ctorres.pokequiz.exception.PokemonNotFoundException;
import com.ctorres.pokequiz.exception.PokemonSpecieNotFound;
import com.ctorres.pokequiz.exception.TypesNotFoundException;
import com.ctorres.pokequiz.dto.pokeapi.GenericList;

@Component
public class PokeApiClient {

    final private RestTemplate restTemplate;
    final private ApiConfig apiConfig;

    public PokeApiClient(RestTemplate restTemplate, ApiConfig apiConfig) {
        this.restTemplate = restTemplate;
        this.apiConfig = apiConfig;
    }

    @Cacheable("pokemon-detail")
    public Pokemon getPokemon(String name) {

        try {

            if (name == null) {
                throw new IllegalArgumentException("Must send a valid pokemon name.");
            }

            final String formattedName = name.toLowerCase().trim();

            String url = apiConfig.getBaseUrl() 
                    + apiConfig.getEndpointGetPokemon() 
                    + "/" 
                    + formattedName;

            Optional<Pokemon> pokemon = Optional
                    .ofNullable(restTemplate.getForObject(url, Pokemon.class));

            if (pokemon.isEmpty()) {
                throw new PokemonNotFoundException(name);
            }

            return pokemon.get();

        } catch (RestClientException e) {
            throw new PokemonClientException();
        }

    }

    @Cacheable("pokemon-list")
    public GenericList getAllPokemon() {

        try {
            String url = apiConfig.getBaseUrl() 
                    + apiConfig.getEndpointGetPokemon() 
                    + "?limit=1500";

            Optional<GenericList> pokemonList = Optional
                    .ofNullable(restTemplate.getForObject(url, GenericList.class));

            if (pokemonList.isEmpty()) {
                throw new PokemonClientException(url);
            }

            if (pokemonList.get().getResults().size() == 0) {
                throw new PokemonNotFoundException();
            }

            return pokemonList.get();

        } catch (RestClientException e) {
            throw new PokemonClientException();
        }
    }

    @Cacheable("types-list")
    public GenericList getAllTypes() {

        try {
            String url = apiConfig.getBaseUrl() 
                    + apiConfig.getEndpointGetType() 
                    + "?limit=30";

            Optional<GenericList> typeList = Optional
                    .ofNullable(restTemplate.getForObject(url, GenericList.class));

            if (typeList.isEmpty()) {
                throw new PokemonClientException(url);
            }

            if (typeList.get().getResults().size() == 0) {
                throw new TypesNotFoundException();
            }

            return typeList.get();

        } catch (RestClientException e) {
            throw new PokemonClientException();
        }
    }

    public Type getType(String typeName) {

        try {
            String url = apiConfig.getBaseUrl() 
                    + apiConfig.getEndpointGetType() 
                    + "/" 
                    + typeName;

            Optional<Type> type = Optional
                    .ofNullable(restTemplate.getForObject(url, Type.class));

            if (type.isEmpty()) {
                throw new TypesNotFoundException(typeName);
            }

            return type.get();

        } catch (RestClientException e) {
            throw new PokemonClientException();
        }
    }

    public PokemonSpecie getPokemonSpecieData(String pokemonName) {

        try {
            String url = apiConfig.getBaseUrl() 
                    + apiConfig.getEndpointGetSpecie() 
                    + "/"
                    + pokemonName;

            Optional<PokemonSpecie> pokemonSpecieData = Optional
                    .ofNullable(restTemplate.getForObject(url, PokemonSpecie.class));

            if (pokemonSpecieData.isEmpty()) {
                throw new PokemonSpecieNotFound(pokemonName);
            }

            return pokemonSpecieData.get();

        } catch (RestClientException e) {
            throw new PokemonClientException();
        }
    }

    @Cacheable("pokemon-colors-list")
    public GenericList getAllPokemonColors() {

        try {
            String url = apiConfig.getBaseUrl() + apiConfig.getEndpointGetColors();

            Optional<GenericList> pokemonList = Optional
                    .ofNullable(restTemplate.getForObject(url, GenericList.class));

            if (pokemonList.isEmpty()) {
                throw new PokemonClientException(url);
            }

            if (pokemonList.get().getResults().size() == 0) {
                throw new PokemonNotFoundException();
            }

            return pokemonList.get();

        } catch (RestClientException e) {
            throw new PokemonClientException();
        }
    }
}