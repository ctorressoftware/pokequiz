package com.ctorres.pokequiz.service.generator;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.springframework.stereotype.Component;

import com.ctorres.pokequiz.client.PokeApiClient;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.dto.api.generator.nameQuestionVariants.NameQuestionVariant;
import com.ctorres.pokequiz.dto.pokeapi.Pokemon;
import com.ctorres.pokequiz.util.PokeApiUtils;

// Rename to Manager, Orchester or other.
// Variants will be renamed for generators.
// The interfaces too.
@Component
public class NameQuestionGenerator implements QuestionGenerator {
    
    final private List<NameQuestionVariant> variants;

    public NameQuestionGenerator(List<NameQuestionVariant> variants) {
        this.variants = variants;
    }

    public GeneratedItem generate() {

        int randomIndex = new Random()
                .nextInt(variants.size());

        NameQuestionVariant questionVariant = variants.get(randomIndex);
        return questionVariant.generate();
    }
    
}
