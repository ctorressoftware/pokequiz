package com.ctorres.pokequiz.application.usecase.core.coordinator;

import com.ctorres.pokequiz.application.usecase.core.generator.GeneratedItem;

import java.util.Locale;

public interface QuestionCoordinator {
    GeneratedItem coordinate(Locale locale);
}
