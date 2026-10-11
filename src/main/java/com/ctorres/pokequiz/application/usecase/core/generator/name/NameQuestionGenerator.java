package com.ctorres.pokequiz.application.usecase.core.generator.name;

import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedItem;

import java.util.Locale;

public interface NameQuestionGenerator {
    GeneratedItem generate(Locale locale);
}
