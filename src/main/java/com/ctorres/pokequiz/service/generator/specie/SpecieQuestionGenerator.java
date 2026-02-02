package com.ctorres.pokequiz.service.generator.specie;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;

import java.util.Locale;

public interface SpecieQuestionGenerator {
    GeneratedItem generate(Locale locale);
}
