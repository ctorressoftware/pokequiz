package com.ctorres.pokequiz.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.coordinator.QuestionCoordinator;

@Service
public class QuizService {

    final private List<QuestionCoordinator> coordinators;
    final private Random random;

    public QuizService(List<QuestionCoordinator> coordinators, Random random) {
        this.coordinators = coordinators;
        this.random = random;
    }

    public GeneratedItem createRandomNameQuestion() {
        final int randomIndex = random.nextInt(coordinators.size());
        QuestionCoordinator coordinator = coordinators.get(randomIndex);
        return coordinator.coordinate();
    }

    public GeneratedItem testGenerator(int index) {
        QuestionCoordinator coordinator = coordinators.get(index);
        return coordinator.coordinate();
    }
}
