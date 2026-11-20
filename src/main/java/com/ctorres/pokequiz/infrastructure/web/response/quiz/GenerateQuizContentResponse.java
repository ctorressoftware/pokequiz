package com.ctorres.pokequiz.infrastructure.web.response.quiz;

import com.ctorres.pokequiz.application.usecase.quiz.model.GeneratedItem;

import java.util.List;

public record GenerateQuizContentResponse(
        Long quizId,
        int questionsQuantity,
        List<GeneratedItem> questions
) {}
