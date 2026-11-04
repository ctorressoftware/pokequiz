package com.ctorres.pokequiz.application.port.in.quiz.create;

import com.ctorres.pokequiz.domain.model.auth.User;

import java.util.UUID;

public record CreateQuizCommand(
        UUID difficultLevelId,
        User user
) {}
