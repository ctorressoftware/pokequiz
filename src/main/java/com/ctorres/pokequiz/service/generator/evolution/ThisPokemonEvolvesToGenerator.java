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

        final String randomPokemonName = "eevee"; /*
                                                   * pokemonList
                                                   * .get(random.nextInt(pokemonList.size()))
                                                   * .getName();
                                                   */

        final PokemonSpecie randomSpecie = client.getPokemonSpecieData(randomPokemonName);
        final String[] evolutionChainUrlParts = randomSpecie.getEvolutionChain().getUrl().split("/");
        final String evolutionId = evolutionChainUrlParts[evolutionChainUrlParts.length - 1];
        final Evolution evolution = client.getEvolutionChain(evolutionId);

        final Evolution.ChainLink root = evolution.getChain();
        final boolean isSpeciePreevolution = root.getSpecies().getName().equals(randomSpecie.getName());
        List<Evolution.ChainLink> evolutions = root.getEvolvesTo();
        String correctOption = "";

        if (!isSpeciePreevolution) {

            for (int i = 0; i < evolutions.size(); i++) {

                final List<Evolution.ChainLink> secondStageEvolutions = evolutions.get(i).getEvolvesTo();
                final String specieName = evolutions.get(i).getSpecies().getName();

                if (specieName.equals(randomSpecie.getName())) {
                    
                    List<Evolution.ChainLink> species = evolutions.get(i).getEvolvesTo();

                    correctOption = species.isEmpty()
                    ? "Not evolve."
                    : species.stream()
                            .findAny()
                            .get()
                            .getSpecies()
                            .getName();
                }

                if (!correctOption.isEmpty()) {
                    break;
                }

                for (int j = 0; j < secondStageEvolutions.size(); j++) {
                    final List<Evolution.ChainLink> thirdStageEvolutions = secondStageEvolutions.get(j).getEvolvesTo();
                    final String specieName2 = thirdStageEvolutions.isEmpty() 
                            ? "Not evolve."
                            : thirdStageEvolutions.get(i).getSpecies().getName();

                    if (!specieName2.isEmpty()) {
                        correctOption = specieName2;
                        break;
                    }
                }
            
                if (!correctOption.isEmpty()) {
                    break;
                }
            }
        } else {
            correctOption = evolutions.isEmpty()
                    ? "Not evolve."
                    : evolutions.stream()
                            .findAny()
                            .get()
                            .getSpecies()
                            .getName();
        }

        final List<String> evolutiosnNames = evolutions.stream() // Cuando no hay evoluciones, ver que hacer ->
                .map(t -> t.getSpecies().getName()) // evolutionChain - 275.
                .toList();

        final List<String> distractorsList = pokemonList.stream()
                .filter(t -> !evolutiosnNames.contains(t.getName()))
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
