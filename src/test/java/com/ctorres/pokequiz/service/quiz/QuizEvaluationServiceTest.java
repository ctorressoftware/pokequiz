package com.ctorres.pokequiz.service.quiz;

import com.ctorres.pokequiz.dto.api.QuizResult;
import com.ctorres.pokequiz.entity.*;
import com.ctorres.pokequiz.service.quiz.evaluation.QuizEvaluationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class QuizEvaluationServiceTest {

    private final QuizEvaluationService quizEvaluationService = new QuizEvaluationService();

    @Test
    void shouldReturnWellCompletedQuizResults() {

        State mockState = new State(
                "COMPLETED",
                "COMPLETED",
                true
        );

        DifficultLevel mockDifficultLevel = new DifficultLevel(
                "NORMAL",
                true
        );

        User mockUser = new User(
                "user",
                "password",
                true
        );

        Quiz mockQuiz = Quiz.builder()
                .initialDate(Instant.now())
                .endDate(null)
                .state(mockState)
                .difficultLevel(mockDifficultLevel)
                .user(mockUser)
                .build();

        var mockQuestion1 = createMockQuestion(
                1L,
                mockQuiz,
                Set.of(createMockAnswer(
                                "description",
                                "answer1",
                                true,
                                true,
                                null
                        ),
                        createMockAnswer(
                                "description",
                                "answer2",
                                false,
                                true,
                                null
                        )
                ),
                null
        );

        var mockQuestion2 = createMockQuestion(
                2L,
                mockQuiz,
                Set.of(
                        createMockAnswer(
                                "description",
                                "answer1",
                                false,
                                true,
                                null),
                        createMockAnswer(
                                "description",
                                "answer2",
                                true,
                                true,
                                null)
                ),
                null
        );

        var mockUserAnswer1 = createMockUserAnswer(
                "description",
                "answer1",
                true,
                mockQuestion1
        );

        var mockUserAnswer2 = createMockUserAnswer(
                "description",
                "answer2",
                true,
                mockQuestion2
        );

        mockQuestion1.setSingleUserAnswer(mockUserAnswer1);
        mockQuestion2.setSingleUserAnswer(mockUserAnswer2);

        List<Question> mockQuestions = List.of(
                mockQuestion1,
                mockQuestion2
        );

        mockQuiz.getQuestions().addAll(mockQuestions);

        QuizResult quizResult = quizEvaluationService.processQuizResult(mockQuiz);

        Map<Long, Boolean> result = new HashMap<>();
        result.put(1L, true);
        result.put(2L, true);

        Assertions.assertEquals(100.0D, quizResult.getScore());
        Assertions.assertEquals(result, quizResult.getResult());
    }

    @Test
    void shouldReturnWrongCompletedQuizResults() {

    }

    @Test
    void shouldReturnNormalCompletedQuizResults() {

    }

    private Question createMockQuestion(
            Long id,
            Quiz quiz,
            Set<Answer> answers,
            Set<UserAnswer> userAnswers) {

        return new Question(
                id,
                "description",
                true,
                "image1.jpg",
                "image2.jpg",
                quiz,
                answers,
                null);
    }

    private Answer createMockAnswer(
            String description,
            String canonicalKey,
            boolean correct,
            boolean active,
            Question question) {

        return new Answer(
                description,
                canonicalKey,
                correct,
                active,
                question);
    }

    private UserAnswer createMockUserAnswer(
            String description,
            String canonicalKey,
            boolean active,
            Question question) {

        return new UserAnswer(
                description,
                canonicalKey,
                active,
                question);
    }
}