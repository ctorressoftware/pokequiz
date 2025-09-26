package com.ctorres.pokequiz.service.generator.evolution;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.dto.api.generator.GeneratedAnswer;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.dto.api.generator.GeneratedQuestion;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.dto.pokeapi.PokemonSpecie;
import com.ctorres.pokequiz.dto.pokeapi.Result;
import com.ctorres.pokequiz.dto.pokeapi.PokemonSpecie.NamedAPIResource;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;

@Component
public class ThisPokemonEvolvesFrom implements EvolutionQuestionGenerator {

    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public ThisPokemonEvolvesFrom(PokeApiClient client, QuestionTextService texts, Random random) {
        this.client = client;
        this.texts = texts;
        this.random = random;
    }

    public GeneratedItem generate() {

        final List<Result> pokemonList = client.getAllPokemon().getResults();

        if (pokemonList == null || pokemonList.size() == 0) {
            throw new IllegalStateException("Required at least one valid pokemon.");
        }

        final String randomPokemonName = pokemonList
                .get(random.nextInt(pokemonList.size()))
                .getName();

        final Pokemon randomPokemon = client.getPokemon(randomPokemonName);
        final PokemonSpecie randomSpecie = client.getPokemonSpecieData(randomPokemon.getSpecies().getName());
        final Optional<NamedAPIResource> preEvolutions = Optional
                .ofNullable(randomSpecie.getEvolvesFromSpecies());

        final String correctOption = preEvolutions.isPresent() 
                ? preEvolutions.get().getName()
                : "It doesn't have pre-evolution.";

        final List<String> distractorsList = pokemonList.stream()
                .map(t -> t.getName())
                .filter(t -> !t.equals(correctOption))
                .toList();

        List<String> distractors = new ArrayList<>(3);

        while (distractors.size() < 3) {
            final int index = random.nextInt(distractorsList.size());
            final String wrongOption = distractorsList.get(index);

            if (!distractors.contains(wrongOption)) {
                distractors.add(wrongOption);
            }
        }

        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.POKEMON_EVOLVES_FROM, Locale.ENGLISH);
        final String questionText = MessageFormat.format(questionTextUnformatted, randomPokemonName);
        final GeneratedQuestion question = new GeneratedQuestion(questionText);

        final List<GeneratedAnswer> answers = new ArrayList<>(2);
        answers.add(new GeneratedAnswer("pokemon-evolves-from:pokemon1", correctOption, true));
        answers.add(new GeneratedAnswer("pokemon-evolves-from:pokemon2", distractors.get(0), false));
        answers.add(new GeneratedAnswer("pokemon-evolves-from:pokemon3", distractors.get(1), false));
        answers.add(new GeneratedAnswer("pokemon-evolves-from:pokemon4", distractors.get(2), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}