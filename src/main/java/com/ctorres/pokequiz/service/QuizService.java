package com.ctorres.pokequiz.service;

import org.springframework.stereotype.Service;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.coordinator.QuestionCoordinator;

@Service
public class QuizService {

    private QuestionCoordinator coordinator;

    public QuizService(QuestionCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public GeneratedItem createRandomNameQuestion() {
        return coordinator.coordinate();
    }
    
}
