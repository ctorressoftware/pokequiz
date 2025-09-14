package com.ctorres.pokequiz.util;

import java.util.random.RandomGenerator;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.config.ApiConfig;

@Component
public class PokeApiUtils {

    final private ApiConfig apiConfig;

    public PokeApiUtils(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public int getRandomPokemonNumber() {
        final int POKEDEX_MIN = apiConfig.getPokedexMinNumber();
        final int POKEDEX_MAX = apiConfig.getPokedexMaxNumber();
        RandomGenerator randomGenerator = RandomGenerator.getDefault();
        int randomPokemonNumber = randomGenerator.nextInt(POKEDEX_MIN, POKEDEX_MAX + 1);
        return randomPokemonNumber;
    }
    
}
