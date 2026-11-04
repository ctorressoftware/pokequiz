package com.ctorres.pokequiz.application.usecase.core.quiz;

import com.ctorres.pokequiz.application.usecase.core.quiz.useranswer.SubmittedUserAnswer;
import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.domain.model.UserAnswer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserAnswerAssembler {

    protected Map<UUID, Question> questionsById(
            Set<Question> questions
    ) {
        return questions.stream()
                .collect(Collectors.toMap(
                        Question::id,
                        Function.identity()
                ));
    }

    protected void validateAllAnswered(
            Set<UUID> questionIds,
            Set<UUID> userAnswerQuestionIds
    ) {
        if (!questionIds.equals(userAnswerQuestionIds)) {
            throw new IllegalArgumentException(
                    "Each quiz's question has to have an answer."
            );
        }
    }

    protected Map<UUID, UserAnswer> createUserAnswersByQuestionId(
            Collection<SubmittedUserAnswer> submissions,
            Map<UUID, Question> questionsById
    ) {
        Map<UUID, UserAnswer> answers = new HashMap<>();

        for (var submission : submissions) {
            UUID questionId = submission.questionId();

            Question question = questionsById.get(questionId);

            if (question == null) {
                throw new IllegalArgumentException(
                        "Invalid questionId=" + questionId
                );
            }

            if (answers.containsKey(questionId)) {
                throw new IllegalArgumentException(
                        "Duplicate answer for questionId=" + questionId
                );
            }

            UserAnswer answer = new UserAnswer(
                    UUID.randomUUID(),
                    submission.description(),
                    submission.value(),
                    true,
                    question
            );

            answers.put(questionId, answer);
        }

        return answers;
    }

    public void applyToQuiz(
            Quiz quiz,
            Map<UUID, UserAnswer> userAnswersByQuestionId
    ) {
        Set<UUID> questionIds = quiz.getQuestions()
                .stream()
                .map(Question::id)
                .collect(Collectors.toSet());

        validateAllAnswered(
                questionIds,
                userAnswersByQuestionId.keySet()
        );

        quiz.getQuestions().forEach(question ->
                question.setSingleUserAnswer(
                        userAnswersByQuestionId.get(question.id())
                )
        );
    }
}