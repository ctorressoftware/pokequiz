package com.ctorres.pokequiz.application.port.in.quiz.complete;

import com.ctorres.pokequiz.application.usecase.core.quiz.useranswer.SubmittedUserAnswer;
import com.ctorres.pokequiz.domain.model.UserAnswer;
import com.ctorres.pokequiz.domain.model.auth.User;

import java.util.Collection;
import java.util.UUID;

public record CompleteQuizCommand(
        UUID quizId,
        Collection<SubmittedUserAnswer> userAnswers,
        User user
) {}
