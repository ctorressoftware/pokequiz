package com.ctorres.pokequiz.service.coordinator;

import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.generator.type.TypeQuestionGenerator;

@Component
public class TypeQuestionCoordinator implements QuestionCoordinator {
    
    final private List<TypeQuestionGenerator> generators;
    final private Random random;

    public TypeQuestionCoordinator(List<TypeQuestionGenerator> generators, Random random) {
        this.generators = generators;
        this.random = random;
    }

    public GeneratedItem coordinate() {
        int randomIndex = random.nextInt(generators.size());        
        TypeQuestionGenerator questionGenerator = generators.get(randomIndex);
        return questionGenerator.generate();
    }
    
}
