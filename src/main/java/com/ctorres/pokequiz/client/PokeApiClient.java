package com.ctorres.pokequiz.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ctorres.pokequiz.config.ApiConfig;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;

@Component
public class PokeApiClient {
    
    final private RestTemplate restTemplate;
    final private ApiConfig apiConfig;

    public PokeApiClient(RestTemplate restTemplate, ApiConfig apiConfig) {
        this.restTemplate = restTemplate;
        this.apiConfig = apiConfig;
    }

    public Pokemon getPokemonByName(String name) {
        
        String url = String.format("%s?pokemon=%s", 
                        apiConfig.getBaseUrl(), name);
        
        Pokemon pokemon = restTemplate.getForObject(url, Pokemon.class);

        return pokemon;
    }
}
