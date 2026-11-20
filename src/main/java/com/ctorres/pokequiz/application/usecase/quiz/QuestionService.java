package com.ctorres.pokequiz.application.usecase.quiz;

import com.ctorres.pokequiz.application.usecase.quiz.model.GeneratedItem;
import com.ctorres.pokequiz.application.usecase.quiz.exception.QuestionQuantityException;
import org.springframework.stereotype.Service;
import com.ctorres.pokequiz.application.usecase.quiz.coordinator.QuestionCoordinator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class QuestionService {
    private final List<QuestionCoordinator> coordinators;
    private final Random random;
    private final Locale DEFAULT_LOCALE = Locale.ENGLISH;
    public static final int MAX_QUESTIONS_QUANTITY = 15;

    public QuestionService(List<QuestionCoordinator> coordinators, Random random) {
        this.coordinators = coordinators;
        this.random = random;
    }

    public List<GeneratedItem> generateQuestions(int questionsQuantity, Locale locale) {

        List<GeneratedItem> questions = new ArrayList<>();

        if (questionsQuantity <= 0) {
            throw new QuestionQuantityException("Quantity must be between one (1) and fifteen (15).");
        }

        if (questionsQuantity > MAX_QUESTIONS_QUANTITY) {
            throw new QuestionQuantityException("Can only generate fifteen (15) questions.");
        }

        var effectiveLocale = (locale != null) ? locale : DEFAULT_LOCALE;

        while (questions.size() < questionsQuantity) {
            int index = random.nextInt(coordinators.size());
            var question = coordinators.get(index).coordinate(effectiveLocale);
            questions.add(question);
        }

        return questions;
    }
}