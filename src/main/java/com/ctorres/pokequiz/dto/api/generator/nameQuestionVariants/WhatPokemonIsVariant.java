package com.ctorres.pokequiz.dto.api.generator.nameQuestionVariants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.dto.api.generator.GeneratedAnswer;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.dto.api.generator.GeneratedQuestion;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;

@Component
public class WhatPokemonIsVariant implements NameQuestionVariant {
    
    final String QUESTION_FORM = "Who's this pokemon?";
    final private PokeApiClient client;

    public WhatPokemonIsVariant(PokeApiClient client) {
        this.client = client;
    }

    private List<GeneratedAnswer> reorderAnswers(List<GeneratedAnswer> answers) {
        Collections.shuffle(answers);
        return answers;
    }

    public GeneratedItem generate() {
        GeneratedItem item = new GeneratedItem();
        GeneratedQuestion question = new GeneratedQuestion(QUESTION_FORM);

        Pokemon pokemon = client.getRandomPokemon();
        
        String name = pokemon.getName();
        List<GeneratedAnswer> answers = new ArrayList<>();
        answers.add(new GeneratedAnswer(name, true));
        answers.add(new GeneratedAnswer("Prueba 2", false));
        answers.add(new GeneratedAnswer("Prueba 3", false));
        answers.add(new GeneratedAnswer("Prueba 4", false));
        answers = reorderAnswers(answers);
        item.setGeneratedQuestion(question);
        item.setGeneratedAnswers(answers);

        return item;
    }
}
