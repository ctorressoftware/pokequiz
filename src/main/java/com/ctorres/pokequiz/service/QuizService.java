package com.ctorres.pokequiz.service;

import java.time.Instant;

import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.StateOption;
import com.ctorres.pokequiz.exception.DifficultLevelNotFoundException;
import com.ctorres.pokequiz.repository.DifficultLevelRepository;
import com.ctorres.pokequiz.repository.QuizRepository;
import com.ctorres.pokequiz.repository.StateRepository;
import com.ctorres.pokequiz.repository.UserRepository;
import com.ctorres.pokequiz.service.security.AuthUser;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private final DifficultLevelRepository difficultLevelRepository;
    private final QuizRepository quizRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    public QuizService(
            DifficultLevelRepository difficultLevelRepository,
            QuizRepository quizRepository,
            StateRepository stateRepository,
            UserRepository userRepository) {
        this.difficultLevelRepository = difficultLevelRepository;
        this.quizRepository = quizRepository;
        this.stateRepository = stateRepository;
        this.userRepository = userRepository;
    }

    public CreateQuizResponse createQuiz(CreateQuizRequest request, AuthUser authenticatedUser) {
        var user = userRepository.getReferenceById(authenticatedUser.getId());
        var state = stateRepository.getReferenceById(StateOption.CREATED.getId());
        var difficultLevelId = request.getDifficultLevelId();

        var difficultLevel = difficultLevelRepository.findById(difficultLevelId)
                .orElseThrow(() -> new DifficultLevelNotFoundException(difficultLevelId));

        var quiz = Quiz.builder()
                .initialDate(Instant.now())
                .endDate(null)
                .state(state)
                .difficultLevel(difficultLevel)
                .user(user)
                .build();

        var inserted = quizRepository.save(quiz);
        return new CreateQuizResponse(inserted.getId());
    }
}
