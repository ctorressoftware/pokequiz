package com.ctorres.pokequiz.service.generator.type;

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
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.dto.pokeapi.GenericList;
import com.ctorres.pokequiz.dto.pokeapi.Result;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;

@Component
public class WhatPokemonTypeIsGenerator implements TypeQuestionGenerator {
    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public WhatPokemonTypeIsGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
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

        final Pokemon pokemon = client.getPokemon(randomPokemonName);
        final List<String> pokemonTypes = pokemon.getTypes()
                .stream()
                .map(type -> type.getType().getName())
                .toList();

        final GenericList allTypes = client.getAllTypes();
        
        final List<Result> typesWithoutCorrects = new ArrayList<>(allTypes.getResults())
                .stream()
                .filter(type -> !pokemonTypes.contains(type.getName()))
                .toList();

        List<String> distractors = new ArrayList<>(3);

        while (distractors.size() < 3) {
            int randomIndex = random.nextInt(typesWithoutCorrects.size()); 
            String wrongType = typesWithoutCorrects.get(randomIndex).getName();
            if (!distractors.contains(wrongType)) {
                distractors.add(wrongType);
            }
        }

        final String correctTypeName = pokemonTypes.getFirst();
        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.WHAT_POKEMON_TYPE_IS, locale);
        final String questionText = MessageFormat.format(questionTextUnformatted, pokemon.getName());
        final GeneratedQuestion question = new GeneratedQuestion(questionText);

        final List<GeneratedAnswer> answers = new ArrayList<>(4);
        answers.add(new GeneratedAnswer("what-pokemon-type-is:type1", correctTypeName, true));
        answers.add(new GeneratedAnswer("what-pokemon-type-is:type2", distractors.get(0), false));
        answers.add(new GeneratedAnswer("what-pokemon-type-is:type3", distractors.get(1), false));
        answers.add(new GeneratedAnswer("what-pokemon-type-is:type4", distractors.get(2), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}
