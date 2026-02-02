package com.ctorres.pokequiz.service.generator.type;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;

import java.util.Locale;

public interface TypeQuestionGenerator {
    GeneratedItem generate(Locale locale);
}
