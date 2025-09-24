package com.ctorres.pokequiz.service.coordinator;

import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.generator.evolution.EvolutionQuestionGenerator;

@Component
public class EvolutionQuestionCoordinator implements QuestionCoordinator {

    final private List<EvolutionQuestionGenerator> generators;
    final private Random random;

    public EvolutionQuestionCoordinator(List<EvolutionQuestionGenerator> generators, Random random) {
        this.generators = generators;
        this.random = random;
    }

    public GeneratedItem coordinate() {
        int randomIndex = random.nextInt(generators.size());        
        EvolutionQuestionGenerator questionGenerator = generators.get(randomIndex);
        return questionGenerator.generate();
    }
    
}
