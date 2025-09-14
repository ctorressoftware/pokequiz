package com.ctorres.pokequiz.service.generator;

import java.util.List;
import java.util.random.RandomGenerator;

import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.dto.api.generator.GeneratedQuestion;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.util.PokeApiUtils;

public class NameQuestionGenerator implements QuestionGenerator{
    
    final private PokeApiClient pokeApiClient;
    final private PokeApiUtils pokeApiUtils;
    final private List<String> QUESTION_PACK = List.of(
        "Who's this pokemon?",
        "Based on the image, who is?",
        "What pokemon of the list doesn't have any relation with this pokemon?",
        "Its name starts with..."
    );

    public NameQuestionGenerator(PokeApiClient pokeApiClient, PokeApiUtils pokeApiUtils) {
        this.pokeApiClient = pokeApiClient;
        this.pokeApiUtils = pokeApiUtils;
    }

    public String writeQuestion(Pokemon pokemon) {
        return "";
    }

    public GeneratedItem generate() {
        int number = pokeApiUtils.getRandomPokemonNumber();
        Pokemon pokemon = pokeApiClient.getPokemon(number);
        String question = QUESTION_PACK.get(RandomGenerator.getDefault().nextInt(0, 5));

        //GeneratedQuestion question = new GeneratedQuestion();

        return null;
    }

    
}
