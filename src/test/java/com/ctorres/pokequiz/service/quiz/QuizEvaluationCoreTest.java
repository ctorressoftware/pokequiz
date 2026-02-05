package com.ctorres.pokequiz.service.quiz;

import com.ctorres.pokequiz.service.quiz.evaluation.EvaluatableQuestion;
import com.ctorres.pokequiz.service.quiz.evaluation.QuizEvaluationCore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class QuizEvaluationCoreTest {

    private final QuizEvaluationCore quizEvaluationCore = new QuizEvaluationCore();

    @Test
    void shouldReturnWellCompletedQuizResults() {
        var first = new EvaluatableQuestion(1L, "correct", "correct");
        var second = new EvaluatableQuestion(2L, "correct", "correct");
        var third = new EvaluatableQuestion(3L, "correct", "correct");
        var fourth = new EvaluatableQuestion(4L, "correct", "correct");
        var questions = List.of(first, second, third, fourth);

        Map<Long, Boolean> result = Map.of(
                1L, true,
                2L, true,
                3L, true,
                4L, true
        );

        var evaluation = quizEvaluationCore.processQuizResult(questions);

        Assertions.assertEquals(100.0D, evaluation.getScore());
        Assertions.assertEquals(result, evaluation.getResult());
    }

    @Test
    void shouldReturnWrongCompletedQuizResults() {
        var first = new EvaluatableQuestion(1L, "correct", "other");
        var second = new EvaluatableQuestion(2L, "correct", "other");
        var third = new EvaluatableQuestion(3L, "correct", "other");
        var fourth = new EvaluatableQuestion(4L, "correct", "other");
        var questions = List.of(first, second, third, fourth);

        Map<Long, Boolean> result = Map.of(
                1L, false,
                2L, false,
                3L, false,
                4L, false
        );

        var evaluation = quizEvaluationCore.processQuizResult(questions);

        Assertions.assertEquals(0.0D, evaluation.getScore());
        Assertions.assertEquals(result, evaluation.getResult());
    }

    @Test
    void shouldReturnNormalCompletedQuizResults() {
        var first = new EvaluatableQuestion(1L, "correct", "correct");
        var second = new EvaluatableQuestion(2L, "correct", "other");
        var third = new EvaluatableQuestion(3L, "correct", "correct");
        var fourth = new EvaluatableQuestion(4L, "correct", "other");
        var questions = List.of(first, second, third, fourth);

        Map<Long, Boolean> result = Map.of(
                1L, true,
                2L, false,
                3L, true,
                4L, false
        );

        var evaluation = quizEvaluationCore.processQuizResult(questions);

        Assertions.assertEquals(50.0D, evaluation.getScore());
        Assertions.assertEquals(result, evaluation.getResult());
    }

    @Test
    void shouldFailWhenDuplicateQuestionIds() {
        var first = new EvaluatableQuestion(1L, "correct", "correct");
        var second = new EvaluatableQuestion(1L, "correct", "other");

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> quizEvaluationCore.processQuizResult(List.of(first, second))
        );
    }

    @Test
    void shouldFailWhenQuestionIdIsNull() {
        var question = new EvaluatableQuestion(null, "correct", "correct");

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> quizEvaluationCore.processQuizResult(List.of(question))
        );
    }
}