package com.ctorres.pokequiz.application.port.in.quiz.getById;

import com.ctorres.pokequiz.application.usecase.core.QuizResult;
import com.ctorres.pokequiz.domain.model.Quiz;

public record GetQuizByIdResult(Quiz quiz, QuizResult result) {}