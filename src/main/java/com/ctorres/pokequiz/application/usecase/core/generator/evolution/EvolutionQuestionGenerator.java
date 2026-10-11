package com.ctorres.pokequiz.application.usecase.core.generator.evolution;

import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedItem;

import java.util.Locale;

public interface EvolutionQuestionGenerator {
    
    public GeneratedItem generate(Locale locale);
}