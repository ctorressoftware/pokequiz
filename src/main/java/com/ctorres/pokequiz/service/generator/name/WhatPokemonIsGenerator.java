package com.ctorres.pokequiz.service.generator.name;

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
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.dto.pokeapi.PokemonList;
import com.ctorres.pokequiz.dto.pokeapi.PokemonResult;
import com.ctorres.pokequiz.dto.pokeapi.Sprites;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;

@Component
public class WhatPokemonIsGenerator implements NameQuestionGenerator {

    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public WhatPokemonIsGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
        this.client = client;
        this.texts = texts;
        this.random = random;
    }

    public GeneratedItem generate() {

        PokemonList pokemonObjectList = client.getAllPokemon();
        List<PokemonResult> pokemonList = pokemonObjectList.getResults();
        List<PokemonResult> distractors = new ArrayList<>();

        PokemonResult pokemonResult = null;

        while (distractors.size() < 4) {
            int randomIndex = random.nextInt(pokemonList.size());
            pokemonResult = pokemonList.get(randomIndex);
            pokemonList.remove(randomIndex);
            distractors.add(pokemonResult);
        }

        String questionText = texts.getRandomText(QuestionKeys.WHAT_POKEMON_IS, Locale.ENGLISH);
        Pokemon correctPokemon = client.getPokemon(distractors.get(0).getName());
        Sprites images = correctPokemon.getSprites();

        GeneratedQuestion question = new GeneratedQuestion(questionText, images.getFrontDefault(), images.getBackDefault());

        List<GeneratedAnswer> answers = new ArrayList<>();
        answers.add(new GeneratedAnswer("what-pokemon-is:name1", correctPokemon.getName(), true));
        answers.add(new GeneratedAnswer("what-pokemon-is:name2", distractors.get(1).getName(), false));
        answers.add(new GeneratedAnswer("what-pokemon-is:name3", distractors.get(2).getName(), false));
        answers.add(new GeneratedAnswer("what-pokemon-is:name4", distractors.get(3).getName(), false));
        Collections.shuffle(answers);

        GeneratedItem item = new GeneratedItem(question, answers);

        return item;
    }
}
