package com.ctorres.pokequiz.service.generator.color;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;

import java.util.Locale;

public interface ColorQuestionGenerator {
    GeneratedItem generate(Locale locale);
}
