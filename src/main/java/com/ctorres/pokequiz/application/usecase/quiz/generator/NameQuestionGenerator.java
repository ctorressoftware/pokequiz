package com.ctorres.pokequiz.application.usecase.quiz.generator;

import com.ctorres.pokequiz.application.usecase.quiz.model.GeneratedItem;

import java.util.Locale;

public interface NameQuestionGenerator {
    GeneratedItem generate(Locale locale);
}
