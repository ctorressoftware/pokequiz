package com.ctorres.pokequiz.application.usecase.quiz.generator;

import com.ctorres.pokequiz.application.usecase.quiz.model.GeneratedItem;

import java.util.Locale;

public interface ColorQuestionGenerator {
    GeneratedItem generate(Locale locale);
}
