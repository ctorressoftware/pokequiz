package com.ctorres.pokequiz.application.port.in.quiz.generate;

import com.ctorres.pokequiz.domain.model.auth.User;

import java.util.Locale;
import java.util.UUID;

public record GenerateQuizCommand(
        UUID quizId,
        int questionsQuantity,
        Locale locale,
        User user
) {}
