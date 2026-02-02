package com.ctorres.pokequiz.service.generator.evolution;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;

import java.util.Locale;

public interface EvolutionQuestionGenerator {
    
    public GeneratedItem generate(Locale locale);
}