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
import com.ctorres.pokequiz.dto.pokeapi.Evolution;
import com.ctorres.pokequiz.dto.pokeapi.PokemonSpecie;
import com.ctorres.pokequiz.dto.pokeapi.Result;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;

@Component
public class ThisPokemonEvolvesToGenerator implements EvolutionQuestionGenerator {

    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public ThisPokemonEvolvesToGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
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

        final PokemonSpecie randomSpecie = client.getPokemonSpecieData(randomPokemonName);
        final String[] evolutionChainUrlParts = randomSpecie.getEvolutionChain().getUrl().split("/");
        final String evolutionId = evolutionChainUrlParts[evolutionChainUrlParts.length - 1];
        final Evolution evolution = client.getEvolutionChain(evolutionId);
        final Evolution.ChainLink root = evolution.getChain();
        
        List<Evolution.ChainLink> evolutions = root.getEvolvesTo();
        boolean isPreevolution = !randomSpecie.getName().equals(root.getSpecies().getName());

        while (isPreevolution) {
            
            final Optional<Evolution.ChainLink> newRoot = evolutions.stream()
                    .filter(t -> t.getSpecies().getName().equals(randomSpecie.getName()))
                    .findFirst();

            if (newRoot.isEmpty()) {
                throw new RuntimeException(); //TODO custom exception
            }

            final String specieName = newRoot.get().getSpecies().getName();
            final boolean isStillPreevolution = !randomSpecie.getName().equals(specieName);

            if (isStillPreevolution) {
                evolutions = newRoot.get().getEvolvesTo();
            } else {
                isPreevolution = false;
            }
        }

        final String correctOption = evolutions.isEmpty() 
                ? "Not evolve." 
                : evolutions.stream()
                    .findAny()
                    .get()
                    .getSpecies()
                    .getName();

        final List<String> evolutiosnNames = evolutions.stream() // Cuando no hay evoluciones, ver que se puede hacer -> evolutionChain - 275.
                .map(t -> t.getSpecies().getName())
                .toList();

        final List<String> distractorsList = pokemonList.stream()
                .filter(t -> evolutiosnNames.contains(t.getName()))
                .map(t -> t.getName())
                .toList();

        List<String> distractors = new ArrayList<>(3);

        while (distractors.size() < 3) {
            final int index = random.nextInt(distractorsList.size());
            final String wrongOption = distractorsList.get(index);
            
            if (!distractors.contains(wrongOption)) {
                distractors.add(wrongOption);
            }
        }

        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.POKEMON_EVOLVES_TO, Locale.ENGLISH);
        final String questionText = MessageFormat.format(questionTextUnformatted, randomPokemonName);
        final GeneratedQuestion question = new GeneratedQuestion(questionText);

        final List<GeneratedAnswer> answers = new ArrayList<>(2);
        answers.add(new GeneratedAnswer("pokemon-evolves-to:pokemon1", correctOption, true));
        answers.add(new GeneratedAnswer("pokemon-evolves-to:pokemon2", distractors.get(0), false));
        answers.add(new GeneratedAnswer("pokemon-evolves-to:pokemon3", distractors.get(1), false));
        answers.add(new GeneratedAnswer("pokemon-evolves-to:pokemon4", distractors.get(2), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }


}
