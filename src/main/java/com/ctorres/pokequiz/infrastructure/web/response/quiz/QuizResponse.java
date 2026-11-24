package com.ctorres.pokequiz.infrastructure.web.response.quiz;

import com.ctorres.pokequiz.application.usecase.quiz.model.EvaluationResult;

public record QuizResponse(
        QuizDto quiz,
        EvaluationResult result
) {}