package com.ctorres.pokequiz.service.quiz.evaluation;

import com.ctorres.pokequiz.dto.api.QuizResult;
import com.ctorres.pokequiz.entity.Answer;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.QuizState;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QuizEvaluationService {

    public QuizResult processQuizResult(Quiz quiz) {

        if (quiz == null || !quiz.getState().getCode().equals(QuizState.COMPLETED.getCode())) {
            throw new IllegalArgumentException("Invalid quiz");
        }

        var processedQuiz = evaluate(quiz);
        var score = calculateScore(processedQuiz);
        return QuizResult.of(processedQuiz, score);
    }

    private Map<Long, Boolean> evaluate(Quiz quiz) {
        var result = new HashMap<Long, Boolean>();

        for (var q : quiz.getQuestions()) {
            var ua = q.getUserAnswers().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Missing user answer for questionId=" + q.getId()));

            var correct = q.getAnswers().stream()
                    .filter(Answer::isCorrect)
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Question without correct answer: " + q.getId()
                            ));

            boolean ok = correct.getCanonicalKey().equals(ua.getCanonicalKey());
            result.put(q.getId(), ok);
        }

        return result;
    }

    private double calculateScore(Map<Long, Boolean> result) {
        var totalQuestions = result.size();
        var correctQuestions = (int) result.values().stream()
                .filter(Boolean::booleanValue)
                .count();

        return ((double) correctQuestions / totalQuestions) * 100;
    }
}
