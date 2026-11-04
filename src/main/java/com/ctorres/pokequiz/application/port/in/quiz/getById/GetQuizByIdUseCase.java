package com.ctorres.pokequiz.application.port.in.quiz.getById;

public interface GetQuizByIdUseCase {
    GetQuizByIdResult getQuizById(GetQuizByIdCommand command);
}
