package com.ctorres.pokequiz.application.port.in.quiz.generate;

import com.ctorres.pokequiz.application.usecase.core.QuizResult;
import com.ctorres.pokequiz.domain.model.Quiz;

public record GenerateQuizResult(Quiz quiz, QuizResult result) {}
