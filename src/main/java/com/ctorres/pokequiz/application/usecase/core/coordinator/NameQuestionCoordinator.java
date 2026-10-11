package com.ctorres.pokequiz.application.usecase.core.coordinator;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedItem;
import com.ctorres.pokequiz.application.usecase.core.generator.name.NameQuestionGenerator;

@Component
public class NameQuestionCoordinator implements QuestionCoordinator {
    
    final private List<NameQuestionGenerator> generators;
    final private Random random;

    public NameQuestionCoordinator(List<NameQuestionGenerator> generators, Random random) {
        this.generators = generators;
        this.random = random;
    }

    public GeneratedItem coordinate(Locale locale) {
        int randomIndex = random.nextInt(generators.size());        
        NameQuestionGenerator questionGenerator = generators.get(randomIndex);
        return questionGenerator.generate(locale);
    }
    
}
