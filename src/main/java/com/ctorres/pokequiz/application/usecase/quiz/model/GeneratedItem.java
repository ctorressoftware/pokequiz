package com.ctorres.pokequiz.application.usecase.quiz.model;

import java.util.List;

public record GeneratedItem (
        GeneratedQuestion question,
        List<GeneratedAnswer> answers
) {}
