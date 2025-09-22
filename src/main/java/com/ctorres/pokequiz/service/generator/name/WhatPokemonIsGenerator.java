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
import com.ctorres.pokequiz.dto.pokeapi.Result;
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

        final List<Result> pokemonList = client.getAllPokemon().getResults();

        if (pokemonList.size() < 4) {
            throw new IllegalStateException("At least four (4) pokemon are required.");
        }

        final List<Result> shuffled = new ArrayList<>(pokemonList);
        Collections.shuffle(shuffled, random);
        final List<Result> distractors = new ArrayList<>(shuffled.subList(0, 4));

        Pokemon correctPokemon = client.getPokemon(distractors.get(0).getName());
        Sprites images = correctPokemon.getSprites();

        final String questionText = texts.getRandomText(QuestionKeys.WHAT_POKEMON_IS, Locale.ENGLISH);
        final GeneratedQuestion question = new GeneratedQuestion(
                questionText,
                images.getFrontDefault(),
                images.getBackDefault());
        /*
         * // Elige como "correcto" el primero con sprites válidos; si no, recorre los
         * demás
         * Pokemon correctPokemon = null;
         * Sprites sprites = null;
         * int correctIdx = -1;
         * for (int i = 0; i < picks.size(); i++) {
         * final String name = picks.get(i).getName();
         * final Pokemon p = client.getPokemon(name);
         * final Sprites s = (p != null ? p.getSprites() : null);
         * final boolean ok = (s != null && s.getFrontDefault() != null &&
         * s.getBackDefault() != null);
         * if (ok) {
         * correctPokemon = p;
         * sprites = s;
         * correctIdx = i;
         * break;
         * }
         * }
         * if (correctPokemon == null) {
         * throw new
         * IllegalStateException("No se encontraron sprites válidos para los candidatos elegidos."
         * );
         * }
         */

        final List<GeneratedAnswer> answers = new ArrayList<>(4);
        answers.add(new GeneratedAnswer("what-pokemon-is:name1", correctPokemon.getName(), true));
        answers.add(new GeneratedAnswer("what-pokemon-is:name2", distractors.get(1).getName(), false));
        answers.add(new GeneratedAnswer("what-pokemon-is:name3", distractors.get(2).getName(), false));
        answers.add(new GeneratedAnswer("what-pokemon-is:name4", distractors.get(3).getName(), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}
