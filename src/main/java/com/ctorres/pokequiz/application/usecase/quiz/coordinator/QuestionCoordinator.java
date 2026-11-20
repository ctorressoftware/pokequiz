package com.ctorres.pokequiz.application.usecase.quiz.coordinator;

import com.ctorres.pokequiz.application.usecase.quiz.model.GeneratedItem;

import java.util.Locale;

public interface QuestionCoordinator {
    GeneratedItem coordinate(Locale locale);
}
