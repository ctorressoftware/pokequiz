package com.ctorres.pokequiz.application.usecase.core.generator.name;

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
import com.ctorres.pokequiz.infrastructure.pokeapi.dto.Result;
import com.ctorres.pokequiz.application.usecase.core.QuestionTextService;
import com.ctorres.pokequiz.application.usecase.core.generator.QuestionKeys;

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

    public GeneratedItem generate(Locale locale) {

        final List<Result> pokemonList = client.getAllPokemon().getResults();

        if (pokemonList.size() < 4) {
            throw new IllegalStateException("At least four (4) pokemon are required.");
        }

        final List<Result> shuffled = new ArrayList<>(pokemonList);
        Collections.shuffle(shuffled, random);
        final List<Result> distractors = new ArrayList<>(shuffled.subList(0, 4));

        Pokemon correctPokemon = client.getPokemon(distractors.get(0).getName());
        Pokemon.Sprites images = correctPokemon.getSprites();

        final String questionText = texts.getRandomText(QuestionKeys.WHAT_POKEMON_IS, locale);
        final GeneratedQuestion question = new GeneratedQuestion(
                questionText,
                images.getFrontDefault(),
                images.getBackDefault());

        final List<GeneratedAnswer> answers = new ArrayList<>(4);
        answers.add(new GeneratedAnswer("what-pokemon-is:name1", correctPokemon.getName(), true));
        answers.add(new GeneratedAnswer("what-pokemon-is:name2", distractors.get(1).getName(), false));
        answers.add(new GeneratedAnswer("what-pokemon-is:name3", distractors.get(2).getName(), false));
        answers.add(new GeneratedAnswer("what-pokemon-is:name4", distractors.get(3).getName(), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}
