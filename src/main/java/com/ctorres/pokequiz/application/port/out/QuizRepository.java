package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.domain.model.QuizState;

import java.util.Optional;
import java.util.UUID;

public interface QuizRepository {
    Quiz save(Quiz quiz);
    Optional<Quiz> findQuizWithQuestionsAndAnswersByIdAndUserId(UUID quizId, UUID userId);
    Optional<Quiz> findQuizByIdAndUserId(UUID quizId, UUID userId);
    int compareAndSetState(UUID quizId, QuizState newStateEntity, QuizState previousStateEntity);
}
