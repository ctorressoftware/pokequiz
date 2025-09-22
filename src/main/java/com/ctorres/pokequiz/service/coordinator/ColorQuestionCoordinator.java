package com.ctorres.pokequiz.service.coordinator;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.generator.color.ColorQuestionGenerator;

@Component
public class ColorQuestionCoordinator implements QuestionCoordinator {

    final private List<ColorQuestionGenerator> generators;
    final private Random random;

    public ColorQuestionCoordinator(List<ColorQuestionGenerator> generators, Random random) {
        this.generators = generators;
        this.random = random;
    }

    public GeneratedItem coordinate() {
        int randomIndex = random.nextInt(generators.size());        
        ColorQuestionGenerator questionGenerator = generators.get(randomIndex);
        return questionGenerator.generate();
    }
    
}
