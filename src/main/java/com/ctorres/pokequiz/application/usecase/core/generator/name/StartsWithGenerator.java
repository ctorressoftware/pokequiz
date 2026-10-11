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
public class StartsWithGenerator implements NameQuestionGenerator {
    final private PokeApiClient client;
    final private QuestionTextService texts;
    final private Random random;

    public StartsWithGenerator(PokeApiClient client, QuestionTextService texts, Random random) {
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
        final Pokemon.Sprites images = pokemon.getSprites();
        final String correctAnswer = String.valueOf(pokemon.getName().charAt(0));

        List<String> alphabet = getAlphabet();
        Collections.shuffle(alphabet);

        final List<String> distractors = new ArrayList<>(3);

        int index = 0;
        while (distractors.size() < 3) {
            final String letter = alphabet.get(index);
            if (!correctAnswer.equals(letter)) {
                distractors.add(letter);
            }
            index++;
        }

        final String questionText = texts.getRandomText(QuestionKeys.NAME_STARTS_WITH, locale);
        final GeneratedQuestion question = new GeneratedQuestion(
                questionText,
                images.getFrontDefault(),
                images.getBackDefault());

        final List<GeneratedAnswer> answers = new ArrayList<>(4);
        answers.add(new GeneratedAnswer("pokemon-starts-with:letter1", correctAnswer, true));
        answers.add(new GeneratedAnswer("pokemon-starts-with:letter2", distractors.get(0), false));
        answers.add(new GeneratedAnswer("pokemon-starts-with:letter3", distractors.get(1), false));
        answers.add(new GeneratedAnswer("pokemon-starts-with:letter4", distractors.get(2), false));
        Collections.shuffle(answers, random);

        return new GeneratedItem(question, answers);
    }

    private List<String> getAlphabet() {
        List<String> alphabet = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.add(String.valueOf(c));
        }
        return alphabet;
    }
}