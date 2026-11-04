package com.ctorres.pokequiz.application.usecase.core.quiz.evaluation;

import com.ctorres.pokequiz.domain.model.Answer;
import com.ctorres.pokequiz.domain.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class EvaluatableQuestionAssembler {

    private static final int EXPECTED_USER_ANSWERS = 1;
    private static final int EXPECTED_CORRECT_ANSWERS = 1;

    public List<EvaluatableQuestion> assemble(Set<Question> questions) {

        if (questions == null || questions.isEmpty())
            throw new IllegalArgumentException("Questions must not be empty");

        return questions.stream().map(question -> {

            if (question.userAnswers().size() != EXPECTED_USER_ANSWERS) {
                throw new IllegalStateException(
                        "Expected user answer: " + EXPECTED_USER_ANSWERS +
                        ", actual user answer: " + question.userAnswers().size() +
                        ", Quiz id: " + question.id());
            }

            var userAnswer = question.userAnswers().iterator().next();

            var correctAnswer = question.answers().stream()
                    .filter(Answer::isCorrect)
                    .toList();

            if (correctAnswer.size() != EXPECTED_CORRECT_ANSWERS) {
                throw new IllegalStateException("Question with multiple correct answers: " + question.id());
            }

            return new EvaluatableQuestion(
                    question.id(),
                    correctAnswer.getFirst().getCanonicalKey(),
                    userAnswer.getCanonicalKey());
        }).toList();
    }
}