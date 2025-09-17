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
import com.ctorres.pokequiz.dto.pokeapi.Sprites;
import com.ctorres.pokequiz.service.QuestionTextService;
import com.ctorres.pokequiz.util.CommonUtils;
import com.ctorres.pokequiz.util.QuestionKeys;

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

    public GeneratedItem generate() {

        final PokemonList pokemonObjectList = client.getAllPokemon();

        if (pokemonObjectList.getCount() == 0) {
            throw new IllegalStateException("Required at least one valid pokemon.");
        }
        
        final int pokemonIndex = random.nextInt(pokemonObjectList.getCount());
        final Pokemon pokemon = client.getPokemon(pokemonIndex);
        final Sprites images = pokemon.getSprites();
        final String correctAnswer = String.valueOf(pokemon.getName().charAt(0));

        List<String> alphabet = CommonUtils.getAlphabet();
        Collections.shuffle(alphabet);
        
        final List<String> distractors = new ArrayList<>(3);
        
        int index = 0;
        while (distractors.size() < 3) {
            final String letter = alphabet.get(index);
            if (correctAnswer != letter) {
                distractors.add(letter);
            }
            index++;
        }

        final String questionText = texts.getRandomText(QuestionKeys.NAME_STARTS_WITH, Locale.ENGLISH);
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
}