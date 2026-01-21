package com.ctorres.pokequiz.service.quiz;

import com.ctorres.pokequiz.dto.api.response.QuizResultResponse;
import com.ctorres.pokequiz.entity.UserAnswer;
import com.ctorres.pokequiz.repository.QuizRepository;
import com.ctorres.pokequiz.repository.UserAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void evaluateQuizAnswers(List<UserAnswer> userAnswers) {}

    public QuizResultResponse evaluateQuiz() {
        return null; // TODO
    }

    private List<UserAnswer> saveUserAnswers(List<UserAnswer> answers) {
        return null; // TODO
    }

    private Long calculateScore(List<UserAnswer> userAnswers) {
        return null; // TODO
    }
}