package com.ctorres.pokequiz.application.port.in.quiz.create;

public interface CreateQuizUseCase {
    CreateQuizResult createQuiz(CreateQuizCommand command);
}
