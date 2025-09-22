package com.ctorres.pokequiz.service.generator.type;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.dto.api.generator.GeneratedAnswer;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.dto.api.generator.GeneratedQuestion;
import com.ctorres.pokequiz.dto.pokeapi.GenericList;
import com.ctorres.pokequiz.dto.pokeapi.Result;
import com.ctorres.pokequiz.dto.pokeapi.Type;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;

public class WhatTypeBeatsMeGenerator implements TypeQuestionGenerator {
    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public WhatTypeBeatsMeGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
        this.client = client;
        this.texts = texts;
        this.random = random;
    }

    public GeneratedItem generate() {

        final GenericList typesObjectList = client.getAllTypes();
        final List<Result> allTypes = typesObjectList.getResults()
                .stream()
                .filter(t -> !List.of("unknown", "shadow").contains(t.getName()))
                .toList();

        final int randomTypeIndex = random.nextInt(typesObjectList.getCount());

        final String randomTypeName = allTypes.get(randomTypeIndex).getName();
        final Type type = client.getType(randomTypeName);
        final Type.DamageRelations relations = type.getDamageRelations();
        List<String> beatenTypes = relations.getDoubleDamageTo()
                .stream()
                .map(t -> t.getName())
                .toList();

        final int randomAnswerIndex = random.nextInt(beatenTypes.size());
        final String correctTypeName = beatenTypes.get(randomAnswerIndex);
        List<String> distractors = new ArrayList<>(3);

        while (distractors.size() < 3) {
            final int randomDistractorsIndex = random.nextInt(typesObjectList.getCount());
            String typeName = allTypes.get(randomDistractorsIndex).getName();
            if (!correctTypeName.equals(typeName) && !beatenTypes.contains(typeName)) {
                distractors.add(typeName);
            }
        }

        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.WHAT_TYPE_BEATS_ME, Locale.ENGLISH);
        final String questionText = MessageFormat.format(questionTextUnformatted, type.getName());
        final GeneratedQuestion question = new GeneratedQuestion(questionText);

        final List<GeneratedAnswer> answers = new ArrayList<>(4);
        answers.add(new GeneratedAnswer("what-type-beats-me:type1", correctTypeName, true));
        answers.add(new GeneratedAnswer("what-type-beats-me:type2", distractors.get(0), false));
        answers.add(new GeneratedAnswer("what-type-beats-me:type3", distractors.get(1), false));
        answers.add(new GeneratedAnswer("what-type-beats-me:type4", distractors.get(2), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}
