package com.ctorres.pokequiz.service.generator.evolution;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.dto.api.generator.GeneratedAnswer;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.dto.api.generator.GeneratedQuestion;
import com.ctorres.pokequiz.dto.pokeapi.Evolution;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.dto.pokeapi.PokemonSpecie;
import com.ctorres.pokequiz.dto.pokeapi.Result;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;

@Component
public class DoesThePokemonEvolveGenerator implements EvolutionQuestionGenerator {

    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public DoesThePokemonEvolveGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
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
        final String[] evolutionChainUrlParts = randomSpecie.getEvolutionChain().getUrl().split("/");
        final String evolutionId = evolutionChainUrlParts[evolutionChainUrlParts.length - 1];
        final Evolution evolution = client.getEvolutionChain(evolutionId);

        final Evolution.ChainLink root = evolution.getChain();
        final boolean isSpeciePreevolution = root.getSpecies().getName().equals(randomSpecie.getName());
        List<Evolution.ChainLink> evolutions = root.getEvolvesTo();
        boolean correctOption = false;

        if (!isSpeciePreevolution) {

            for (int i = 0; i < evolutions.size(); i++) {

                final String specieName = evolutions.get(i).getSpecies().getName();

                if (specieName.equals(randomSpecie.getName())) {
                    List<Evolution.ChainLink> species = evolutions.get(i).getEvolvesTo();
                    correctOption = !species.isEmpty();
                    break;
                }

                final List<Evolution.ChainLink> secondStageEvolutions = evolutions.get(i).getEvolvesTo();

                if (!secondStageEvolutions.isEmpty()) {
                    final List<Evolution.ChainLink> thirdStageEvolutions = secondStageEvolutions.get(0).getEvolvesTo();
                    final boolean specieName2 = thirdStageEvolutions.isEmpty();
                    correctOption = !specieName2;
                    break;
                }
            }
        } else {
            correctOption = !evolutions.isEmpty();
        }

        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.DOES_POKEMON_EVOLVE, Locale.ENGLISH);
        final String questionText = MessageFormat.format(questionTextUnformatted, randomPokemonName);
        final GeneratedQuestion question = new GeneratedQuestion(questionText);

        final List<GeneratedAnswer> answers = new ArrayList<>(2);
        answers.add(new GeneratedAnswer("does-pokemon-evolve:option1", String.valueOf(correctOption), true));
        answers.add(new GeneratedAnswer("does-pokemon-evolve:option2", String.valueOf(!correctOption), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}
