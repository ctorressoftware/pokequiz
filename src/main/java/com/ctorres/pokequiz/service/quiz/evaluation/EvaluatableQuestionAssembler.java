package com.ctorres.pokequiz.service.quiz.evaluation;

import com.ctorres.pokequiz.entity.Answer;
import com.ctorres.pokequiz.entity.Question;
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

            if (question.getUserAnswers().size() != EXPECTED_USER_ANSWERS) {
                throw new IllegalStateException(
                        "Expected user answer: " + EXPECTED_USER_ANSWERS +
                        ", actual user answer: " + question.getUserAnswers().size() +
                        ", Quiz id: " + question.getId());
            }

            var userAnswer = question.getUserAnswers().iterator().next();

            var correctAnswer = question.getAnswers().stream()
                    .filter(Answer::isCorrect)
                    .toList();

            if (correctAnswer.size() != EXPECTED_CORRECT_ANSWERS) {
                throw new IllegalStateException("Question with multiple correct answers: " + question.getId());
            }

            return new EvaluatableQuestion(
                    question.getId(),
                    correctAnswer.getFirst().getCanonicalKey(),
                    userAnswer.getCanonicalKey());
        }).toList();
    }
}