package com.ctorres.pokequiz.service;

import org.springframework.stereotype.Service;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.generator.NameQuestionGenerator;
import com.ctorres.pokequiz.service.generator.QuestionGenerator;

@Service
public class QuizService {

    private QuestionGenerator generator;

    public QuizService(QuestionGenerator generator) {
        this.generator = generator;
    }

    public GeneratedItem createRandomNameQuestion() {
        return generator.generate();
    }
    
}
