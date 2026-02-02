package com.ctorres.pokequiz.service.coordinator;

import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;

import java.util.Locale;

public interface QuestionCoordinator {
    GeneratedItem coordinate(Locale locale);
}
