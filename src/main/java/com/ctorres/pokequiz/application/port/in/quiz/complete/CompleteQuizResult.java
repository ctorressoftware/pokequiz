package com.ctorres.pokequiz.application.port.in.quiz.complete;

import com.ctorres.pokequiz.application.usecase.quiz.model.EvaluationResult;
import com.ctorres.pokequiz.domain.model.Quiz;

public record CompleteQuizResult(Quiz quiz, EvaluationResult result) {}
