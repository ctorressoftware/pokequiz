package com.ctorres.pokequiz.service.quiz;

import java.time.Instant;
import java.util.*;

import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.EvaluateAnswersRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.EvaluateAnswersResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.QuizState;
import com.ctorres.pokequiz.exception.*;
import com.ctorres.pokequiz.mapper.QuestionMapper;
import com.ctorres.pokequiz.mapper.UserAnswerMapper;
import com.ctorres.pokequiz.repository.*;
import com.ctorres.pokequiz.service.security.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizService {
    private final QuizContentPersister quizContentPersister;
    private final QuizEvaluationService quizEvaluationService;
    private final QuizStateService quizStateService;
    private final QuizRepository quizRepository;
    private final DifficultLevelRepository difficultLevelRepository;
    private final StateRepository stateRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;

    public QuizService(
            QuizEvaluationService quizEvaluationService,
            QuizContentPersister quizContentPersister,
            QuizStateService quizStateService,
            QuizRepository quizRepository,
            DifficultLevelRepository difficultLevelRepository,
            StateRepository stateRepository,
            UserAnswerRepository userAnswerRepository,
            UserRepository userRepository) {
        this.difficultLevelRepository = difficultLevelRepository;
        this.quizEvaluationService = quizEvaluationService;
        this.quizContentPersister = quizContentPersister;
        this.quizStateService = quizStateService;
        this.quizRepository = quizRepository;
        this.stateRepository = stateRepository;
        this.userAnswerRepository = userAnswerRepository;
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

        final var quiz = quizRepository
                .findQuizByIdAndUserId(request.getQuizId(), user.getId())
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        var insertedQuestions = quizContentPersister
                .createAndSaveQuizContent(quiz, questionsQuantity);

        return buildQuizDtoResponse(quiz, insertedQuestions);
    }

    @Transactional
    public EvaluateAnswersResponse completeQuizAnswers(EvaluateAnswersRequest request, AuthUser user) {
        final var quizId = request.getQuizId();
        final var userAnswerDtos = request.getUserAnswersDtos();

        if (quizId == null || quizId <= 0) {
            throw new BadRequestException("Invalid quizId field.");
        }

        if (userAnswerDtos == null || userAnswerDtos.isEmpty()) {
            throw new BadRequestException("Invalid answers field.");
        }

        final var quiz = quizRepository
                .findQuizWithQuestionsAndAnswersByIdAndUserId(quizId, user.getId())
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        quizStateService.markInProgress(quiz);
        final var questionsById = quizEvaluationService.questionsById(quiz.getQuestions());
        final var userAnswers = UserAnswerMapper.toDomain(userAnswerDtos, questionsById)
                .stream()
                .toList();

        final var insertedUserAnswers = userAnswerRepository.saveAll(userAnswers); // TODO comment to test

        final var quizResult = quizEvaluationService // TODO pass questionsById, and not getQuestions again.
                .evaluateUserAnswers(quiz.getQuestions(), insertedUserAnswers);

        final var score = quizEvaluationService.calculateScore(quizResult);
        quizStateService.markCompleted(quiz);
        return new EvaluateAnswersResponse(quizResult, score);
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