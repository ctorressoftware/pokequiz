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
import com.ctorres.pokequiz.dto.pokeapi.Result;
import com.ctorres.pokequiz.dto.pokeapi.Type;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;

@Component
public class WhatTypeBeatsGenerator implements TypeQuestionGenerator {

    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public WhatTypeBeatsGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
        this.client = client;
        this.texts = texts;
        this.random = random;
    }

    public GeneratedItem generate() {

        final List<Result> allTypes = client.getAllTypes().getResults()
                .stream()
                .filter(t -> !List.of("unknown", "shadow").contains(t.getName()))
                .toList();

        final String randomTypeName = allTypes
                .get(random.nextInt(allTypes.size()))
                .getName();
        
        final Type type = client.getType(randomTypeName);
        final Type.DamageRelations relations = type.getDamageRelations();
        final List<String> beatenTypes = relations.getDoubleDamageTo()
                .stream()
                .map(t -> t.getName())
                .toList();

        final String correctTypeName = beatenTypes.get(random.nextInt(beatenTypes.size()));
        List<String> distractors = new ArrayList<>(3);

        while (distractors.size() < 3) {
            final int index = random.nextInt(allTypes.size());
            String wrongTypeName = allTypes.get(index).getName();
            if (!correctTypeName.equals(wrongTypeName) && !beatenTypes.contains(wrongTypeName)) {
                distractors.add(wrongTypeName);
            }
        }

        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.WHAT_TYPE_BEATS, Locale.ENGLISH);
        final String questionText = MessageFormat.format(questionTextUnformatted, type.getName());
        final GeneratedQuestion question = new GeneratedQuestion(questionText);
        
        final List<GeneratedAnswer> answers = new ArrayList<>(4);
        answers.add(new GeneratedAnswer("what-type-beats:type1", correctTypeName, true));
        answers.add(new GeneratedAnswer("what-type-beats:type2", distractors.get(0), false));
        answers.add(new GeneratedAnswer("what-type-beats:type3", distractors.get(1), false));
        answers.add(new GeneratedAnswer("what-type-beats:type4", distractors.get(2), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }
}
