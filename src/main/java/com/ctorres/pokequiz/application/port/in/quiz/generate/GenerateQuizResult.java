package com.ctorres.pokequiz.application.port.in.quiz.generate;

import com.ctorres.pokequiz.application.usecase.quiz.model.EvaluationResult;
import com.ctorres.pokequiz.domain.model.Quiz;

public record GenerateQuizResult(Quiz quiz, EvaluationResult result) {}
