package com.ctorres.pokequiz.service.quiz.evaluation;

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

    private Map<Long, Boolean> evaluate(List<EvaluatableQuestion> evaluatableQuestions) {
        return evaluatableQuestions.stream()
                .collect(Collectors.toMap(
                        eq -> {
                            if (eq.getQuestionId() == null) throw new IllegalStateException("questionId is required");
                            if (eq.getUserAnswer() == null) throw new IllegalStateException("userAnswer is required");
                            if (eq.getCorrectAnswer() == null)
                                throw new IllegalStateException("correctAnswer is required");
                            return eq.getQuestionId();
                        },
                        eq -> eq.getUserAnswer().equals(eq.getCorrectAnswer()),
                        (a, b) -> {
                            throw new IllegalStateException("Duplicate questions to evaluate");
                        },
                        LinkedHashMap::new
                ));
    }

    private double calculateScore(Map<Long, Boolean> result) {
        long correct = result.values().stream().filter(Boolean::booleanValue).count();
        return (correct * 100.0) / result.size();
    }
}
