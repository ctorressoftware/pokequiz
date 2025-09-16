package com.ctorres.pokequiz.service.coordinator;

import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.generator.name.NameQuestionGenerator;

@Component
public class NameQuestionCoordinator implements QuestionCoordinator {
    
    final private List<NameQuestionGenerator> generators;
    final private Random random;

    public NameQuestionCoordinator(List<NameQuestionGenerator> generators, Random random) {
        this.generators = generators;
        this.random = random;
    }

    public GeneratedItem coordinate() {
        int randomIndex = random.nextInt(generators.size());        
        NameQuestionGenerator questionVariant = generators.get(randomIndex);
        return questionVariant.generate();
    }
    
}
