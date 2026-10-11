package com.ctorres.pokequiz.application.usecase.core.coordinator;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedItem;
import com.ctorres.pokequiz.application.usecase.core.generator.specie.SpecieQuestionGenerator;

@Component
public class SpecieQuestionCoordinator implements QuestionCoordinator {
    
    final private List<SpecieQuestionGenerator> generators;
    final private Random random;

    public SpecieQuestionCoordinator(List<SpecieQuestionGenerator> generators, Random random) {
        this.generators = generators;
        this.random = random;
    }

    public GeneratedItem coordinate(Locale locale) {
        int randomIndex = random.nextInt(generators.size());        
        SpecieQuestionGenerator questionGenerator = generators.get(randomIndex);
        return questionGenerator.generate(locale);
    }
    
}
