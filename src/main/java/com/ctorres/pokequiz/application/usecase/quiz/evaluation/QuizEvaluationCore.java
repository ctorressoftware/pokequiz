package com.ctorres.pokequiz.application.usecase.quiz.evaluation;

import com.ctorres.pokequiz.application.usecase.quiz.model.EvaluatableQuestion;
import com.ctorres.pokequiz.application.usecase.quiz.model.EvaluationResult;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public final class QuizEvaluationCore {

    public EvaluationResult processQuizResult(List<EvaluatableQuestion> evaluatableQuestions) {
        if (evaluatableQuestions == null || evaluatableQuestions.isEmpty())
            throw new IllegalArgumentException("Invalid questions to evaluate");

        var result = evaluate(evaluatableQuestions);
        return EvaluationResult.of(result, calculateScore(result));
    }

    private Map<UUID, Boolean> evaluate(List<EvaluatableQuestion> evaluatableQuestions) {
        return evaluatableQuestions.stream()
                .collect(Collectors.toMap(
                        eq -> {
                            if (eq.questionId() == null) throw new IllegalStateException("questionId is required");
                            if (eq.userAnswer() == null) throw new IllegalStateException("userAnswer is required");
                            if (eq.correctAnswer() == null)
                                throw new IllegalStateException("correctAnswer is required");
                            return eq.questionId();
                        },
                        eq -> eq.userAnswer().equals(eq.correctAnswer()),
                        (a, b) -> {
                            throw new IllegalStateException("Duplicate questions to evaluate");
                        },
                        LinkedHashMap::new
                ));
    }

    private double calculateScore(Map<UUID, Boolean> result) {
        long correct = result.values().stream().filter(Boolean::booleanValue).count();
        return (correct * 100.0) / result.size();
    }
}
