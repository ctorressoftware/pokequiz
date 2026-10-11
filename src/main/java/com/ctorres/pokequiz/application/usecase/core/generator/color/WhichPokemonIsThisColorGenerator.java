package com.ctorres.pokequiz.service.generator.color;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.service.generator.GeneratedAnswer;
import com.ctorres.pokequiz.service.generator.GeneratedItem;
import com.ctorres.pokequiz.service.generator.GeneratedQuestion;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.dto.pokeapi.PokemonSpecie;
import com.ctorres.pokequiz.dto.pokeapi.Result;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.QuestionKeys;
import org.springframework.stereotype.Component;

@Component
public class WhichPokemonIsThisColorGenerator implements ColorQuestionGenerator {
    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public WhichPokemonIsThisColorGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
        this.client = client;
        this.texts = texts;
        this.random = random;
    }

    public GeneratedItem generate(Locale locale) {
        final List<Result> pokemonList = client.getAllPokemon().getResults();
        final List<Result> colorList = client.getAllPokemonColors().getResults();

        if (pokemonList == null || pokemonList.isEmpty()) {
            throw new IllegalStateException("Required at least one valid pokemon.");
        }

        if (colorList == null || colorList.isEmpty()) {
            throw new IllegalStateException("Required at least one valid color.");
        }

        final String randomPokemonName = pokemonList
                .get(random.nextInt(pokemonList.size()))
                .getName();

        final Pokemon randomPokemon = client.getPokemon(randomPokemonName);
        final PokemonSpecie specie = client.getPokemonSpecieData(randomPokemon.getSpecies().getName());

        final String correctOption = specie.getColor().getName();
        final List<String> distractors = new ArrayList<>(3);

        while (distractors.size() < 3) {
            final int index = random.nextInt(colorList.size());
            final String wrongOption = colorList.get(index).getName();
            if (!correctOption.equals(wrongOption)) {
                distractors.add(wrongOption);
            }
        }

        final String questionTextUnformatted = texts.getRandomText(QuestionKeys.WHAT_IS_THE_COLOR_OF, locale);
        final String questionText = MessageFormat.format(questionTextUnformatted, randomPokemonName);
        final GeneratedQuestion question = new GeneratedQuestion(questionText);

        final List<GeneratedAnswer> answers = new ArrayList<>(4);
        answers.add(new GeneratedAnswer("what-is-the-color-of:color1", correctOption, true));
        answers.add(new GeneratedAnswer("what-is-the-color-of:color2", distractors.get(0), false));
        answers.add(new GeneratedAnswer("what-is-the-color-of:color3", distractors.get(1), false));
        answers.add(new GeneratedAnswer("what-is-the-color-of:color4", distractors.get(2), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }    
}
