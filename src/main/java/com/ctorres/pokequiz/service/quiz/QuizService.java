package com.ctorres.pokequiz.service.quiz;

import java.time.Instant;
import java.util.Collection;
import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.QuizState;
import com.ctorres.pokequiz.exception.*;
import com.ctorres.pokequiz.mapper.QuestionMapper;
import com.ctorres.pokequiz.repository.*;
import com.ctorres.pokequiz.service.security.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizService {
    private final QuizContentPersister quizContentPersister;
    private final QuizRepository quizRepository;
    private final DifficultLevelRepository difficultLevelRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    public QuizService(
            QuizContentPersister quizContentPersister,
            QuizRepository quizRepository,
            DifficultLevelRepository difficultLevelRepository,
            StateRepository stateRepository,
            UserRepository userRepository) {
        this.difficultLevelRepository = difficultLevelRepository;
        this.quizContentPersister = quizContentPersister;
        this.quizRepository = quizRepository;
        this.stateRepository = stateRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreateQuizResponse createQuiz(CreateQuizRequest request, AuthUser authenticatedUser) {
        final var user = userRepository.getReferenceById(authenticatedUser.getId());
        final var state = stateRepository.getReferenceById(QuizState.CREATED.getId());
        final var difficultLevelId = request.getDifficultLevelId();

        final var difficultLevel = difficultLevelRepository.findById(difficultLevelId)
                .orElseThrow(() -> new DifficultLevelNotFoundException(difficultLevelId));

        final var quiz = Quiz.builder()
                .initialDate(Instant.now())
                .endDate(null)
                .state(state)
                .difficultLevel(difficultLevel)
                .user(user)
                .build();

        final var inserted = quizRepository.save(quiz);
        return new CreateQuizResponse(inserted.getId());
    }

    public QuizDtoResponse getQuizById(Long quizId, AuthUser user) {

        if (quizId == null || quizId <= 0) {
            throw new BadRequestException("Invalid quizId field.");
        }

        final var optionalQuiz = quizRepository
                .findQuizWithQuestionsAndAnswersByIdAndUserId(quizId, user.getId());

        if (optionalQuiz.isEmpty()) {
            throw new QuizNotFoundException(quizId);
        }

        final var quiz = optionalQuiz.get();

        return buildQuizDtoResponse(quiz, quiz.getQuestions());
    }

    @Transactional
    public QuizDtoResponse generateAndSaveContent(GenerateQuizContentRequest request, AuthUser user) {
        final Long quizId = request.getQuizId();
        final int questionsQuantity = request.getQuestionsQuantity();

        if (quizId == null || quizId <= 0) {
            throw new BadRequestException("Invalid quizId field.");
        }

        if (questionsQuantity <= 0) {
            throw new BadRequestException("Invalid questionsQuantity field.");
        }

        final var optionalQuiz = quizRepository
                .findQuizByIdAndUserId(request.getQuizId(), user.getId());

        if (optionalQuiz.isEmpty()) {
            throw new QuizNotFoundException(quizId);
        }

        final var quiz = optionalQuiz.get();
        var insertedQuestions = quizContentPersister
                .createAndSaveQuizContent(quiz, questionsQuantity);

        return buildQuizDtoResponse(quiz, insertedQuestions);
    }

    private QuizDtoResponse buildQuizDtoResponse(Quiz quiz, Collection<Question> questions) {
        return QuizDtoResponse.builder()
                .quizId(quiz.getId())
                .initialDate(quiz.getInitialDate())
                .endDate(quiz.getEndDate())
                .state(quiz.getState().getDescription())
                .difficultLevel(quiz.getDifficultLevel().getDescription())
                .questions(QuestionMapper.toDto(questions))
                .build();
    }
}