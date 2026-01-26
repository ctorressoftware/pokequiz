package com.ctorres.pokequiz.service.quiz;

import com.ctorres.pokequiz.entity.Answer;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.UserAnswer;
import com.ctorres.pokequiz.repository.QuizRepository;
import com.ctorres.pokequiz.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizEvaluationService {

    private final QuizRepository quizRepository;
    private final UserAnswerRepository userAnswerRepository;

    public QuizEvaluationService(
            QuizRepository quizRepository,
            UserAnswerRepository userAnswerRepository) {
        this.quizRepository = quizRepository;
        this.userAnswerRepository = userAnswerRepository;
    }

    protected Map<Long, Question> questionsById(Set<Question> questions) {
        return questions.stream()
                .collect(Collectors.toMap(
                        Question::getId,
                        q -> q
                ));
    }

    private Map<Long, Set<Answer>> correctAnswersByQuestionId(Set<Question> questions) {
        return questions.stream()
                .collect(Collectors.toMap(
                        Question::getId,
                        q -> q.getAnswers().stream()
                                .filter(Answer::isCorrect)
                                .collect(Collectors.toUnmodifiableSet())
                ));
    }

    public Map<Long, Boolean> evaluateUserAnswers(
            Map<Long, Question> questionsById,
            List<UserAnswer> userAnswers) {

        final var correctAnswers = correctAnswersByQuestionId(new HashSet<>(questionsById.values()));

        return userAnswers.stream()
                .collect(Collectors.toMap(
                        UserAnswer::getId,
                        q -> {
                            var correctSet = correctAnswers.get(q.getQuestion().getId());

                            return correctSet.stream()
                                    .anyMatch(correct ->
                                            correct.getCanonicalKey()
                                                    .equals(q.getCanonicalKey())
                                    );
                        }
                ));
    }

    private List<UserAnswer> saveUserAnswers(List<UserAnswer> answers) {
        return null; // TODO
    }

    protected int calculateScore(Map<Long, Boolean> result) {
        return 100; // TODO
    }
}