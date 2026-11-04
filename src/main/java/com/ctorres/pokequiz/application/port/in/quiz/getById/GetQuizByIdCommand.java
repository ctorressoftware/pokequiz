package com.ctorres.pokequiz.application.port.in.quiz.getById;

import com.ctorres.pokequiz.domain.model.auth.User;

import java.util.UUID;

public record GetQuizByIdCommand(UUID quizId, User user) {}
