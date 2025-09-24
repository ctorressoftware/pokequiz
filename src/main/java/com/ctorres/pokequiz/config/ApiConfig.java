package com.ctorres.pokequiz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {
    
    private String baseUrl;
    private int pokedexMinNumber;
    private int pokedexMaxNumber;
    private String endpointGetPokemon;
    private String endpointGetType;
    private String endpointGetSpecie;
    private String endpointGetColors;
    private String endpointGetEvolutionChain;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setEndpointGetPokemon(String endpointGetPokemon) {
        this.endpointGetPokemon = endpointGetPokemon;
    }

    public String getEndpointGetPokemon() {
        return endpointGetPokemon;
    }

    public void setEndpointGetType(String endpointGetType) {
        this.endpointGetType = endpointGetType;
    }

    public String getEndpointGetType() {
        return endpointGetType;
    }

    public void setEndpointGetSpecie(String endpointGetSpecie) {
        this.endpointGetSpecie = endpointGetSpecie;
    }

    public String getEndpointGetSpecie() {
        return endpointGetSpecie;
    }

    public void setEndpointGetColors(String endpointGetColors) {
        this.endpointGetColors = endpointGetColors;
    }

    public String getEndpointGetColors() {
        return endpointGetColors;
    }

    public void setEndpointGetEvolutionChain(String endpointGetEvolutionChain) {
        this.endpointGetEvolutionChain = endpointGetEvolutionChain;
    }

    public String getEndpointGetEvolutionChain() {
        return endpointGetEvolutionChain;
    }

    public void setPokedexMinNumber(int pokedexMinNumber) {
        this.pokedexMinNumber = pokedexMinNumber;
    }

    public int getPokedexMinNumber() {
        return pokedexMinNumber;
    }

    public void setPokedexMaxNumber(int pokedexMaxNumber) {
        this.pokedexMaxNumber = pokedexMaxNumber;
    }

    public int getPokedexMaxNumber() {
        return pokedexMaxNumber;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}