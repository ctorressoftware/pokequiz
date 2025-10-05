package com.ctorres.pokequiz.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.service.QuizService;
import com.ctorres.pokequiz.util.Constants;

@RestController
@RequestMapping(Constants.POKEMON)
public class PokequizController {

    final private QuizService quizService;

    public PokequizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/generateRandomNameQuestion")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneratedItem generateRandomNameQuestion() {
        return quizService.createRandomNameQuestion();
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public GeneratedItem testGenerator(int index) {
        return quizService.testGenerator(index);
    }

}
