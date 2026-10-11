package com.ctorres.pokequiz.application.usecase.core.generator.specie;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.infrastructure.pokeapi.PokeApiClient;
import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedAnswer;
import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedItem;
import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedQuestion;
import com.ctorres.pokequiz.infrastructure.pokeapi.dto.Pokemon;
import com.ctorres.pokequiz.infrastructure.pokeapi.dto.PokemonSpecie;
import com.ctorres.pokequiz.infrastructure.pokeapi.dto.Result;
import com.ctorres.pokequiz.application.usecase.core.QuestionTextService;
import com.ctorres.pokequiz.application.usecase.core.generator.QuestionKeys;

@Component
public class IsPokemonBabyGenerator implements SpecieQuestionGenerator {
    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public IsPokemonBabyGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
        this.client = client;
        this.texts = texts;
        this.random = random;
    }

    public GeneratedItem generate(Locale locale) {

        final List<Result> pokemonList = client.getAllPokemon().getResults();

        if (pokemonList == null || pokemonList.isEmpty()) {
            throw new IllegalStateException("Required at least one valid pokemon.");
        }

        final String randomPokemonName = pokemonList
                .get(random.nextInt(pokemonList.size()))
                .getName();

        final Pokemon randomPokemon = client.getPokemon(randomPokemonName);

        final PokemonSpecie specieData = client.getPokemonSpecieData(randomPokemon.getSpecies().getName());
        final String correctOption = String.valueOf(specieData.isBaby());
        final String wrongOption = String.valueOf(!specieData.isBaby());

        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.IS_POKEMON_BABY, locale);
        final String questionText = MessageFormat.format(questionTextUnformatted, randomPokemonName);
        final GeneratedQuestion question = new GeneratedQuestion(questionText);

        final List<GeneratedAnswer> answers = new ArrayList<>(2);
        answers.add(new GeneratedAnswer("is-pokemon-baby:option1", correctOption, true));
        answers.add(new GeneratedAnswer("is-pokemon-baby:option2", wrongOption, false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}
