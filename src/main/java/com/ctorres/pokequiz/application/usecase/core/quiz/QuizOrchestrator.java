package com.ctorres.pokequiz.application.usecase.core.quiz;

import java.time.Instant;
import java.util.*;

import com.ctorres.pokequiz.application.usecase.core.QuizResult;
import com.ctorres.pokequiz.domain.exception.DifficultLevelNotFoundException;
import com.ctorres.pokequiz.domain.exception.QuizNotFoundException;
import com.ctorres.pokequiz.infrastructure.web.request.quiz.CreateQuizRequest;
import com.ctorres.pokequiz.infrastructure.web.request.quiz.EvaluateAnswersRequest;
import com.ctorres.pokequiz.infrastructure.web.request.quiz.GenerateQuizContentRequest;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.CreateQuizResponse;
import com.ctorres.pokequiz.infrastructure.web.response.quiz.QuizDtoResponse;
import com.ctorres.pokequiz.infrastructure.persistence.entity.Quiz;
import com.ctorres.pokequiz.infrastructure.persistence.repository.*;
import com.ctorres.pokequiz.application.usecase.core.mapper.QuestionMapper;
import com.ctorres.pokequiz.application.usecase.core.quiz.evaluation.EvaluatableQuestionAssembler;
import com.ctorres.pokequiz.application.usecase.core.quiz.evaluation.QuizEvaluationCore;
import com.ctorres.pokequiz.application.usecase.core.quiz.state.QuizStateService;
import com.ctorres.pokequiz.infrastructure.security.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizOrchestrator {
    private final DifficultLevelRepository difficultLevelRepository;
    private final EvaluatableQuestionAssembler evaluatableQuestionAssembler;
    private final QuizContentPersister quizContentPersister;
    private final QuizEvaluationCore quizEvaluationCore;
    private final QuizStateService quizStateService;
    private final QuizRepository quizRepository;
    private final StateRepository stateRepository;
    private final UserAnswerAssembler userAnswerAssembler;
    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;

    public QuizOrchestrator(
            DifficultLevelRepository difficultLevelRepository,
            EvaluatableQuestionAssembler evaluatableQuestionAssembler,
            QuizEvaluationCore quizEvaluationCore,
            QuizContentPersister quizContentPersister,
            QuizStateService quizStateService,
            QuizRepository quizRepository,
            StateRepository stateRepository,
            UserAnswerAssembler userAnswerAssembler,
            UserAnswerRepository userAnswerRepository,
            UserRepository userRepository) {
        this.difficultLevelRepository = difficultLevelRepository;
        this.evaluatableQuestionAssembler = evaluatableQuestionAssembler;
        this.quizEvaluationCore = quizEvaluationCore;
        this.quizContentPersister = quizContentPersister;
        this.quizStateService = quizStateService;
        this.quizRepository = quizRepository;
        this.stateRepository = stateRepository;
        this.userAnswerAssembler = userAnswerAssembler;
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
            throw new IllegalArgumentException("Invalid quizId field.");
        }

        final var quiz = quizRepository
                .findQuizWithQuestionsAndAnswersByIdAndUserId(quizId, user.getId())
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        if (quiz.getState().getCode().equals(QuizState.COMPLETED.getCode())) {
            var toEvaluate = evaluatableQuestionAssembler.assemble(quiz.getQuestions());
            var evaluation = quizEvaluationCore.processQuizResult(toEvaluate);
            return buildQuizDtoResponse(quiz, QuizResult.of(evaluation.getResult(), evaluation.getScore()));
        }

        return buildQuizDtoResponse(quiz, null);
    }

    @Transactional
    public QuizDtoResponse generateAndSaveContent(GenerateQuizContentRequest request, Locale locale, AuthUser user) {
        final Long quizId = request.getQuizId();
        final int questionsQuantity = request.getQuestionsQuantity();

        if (quizId == null || quizId <= 0) {
            throw new IllegalArgumentException("Invalid quizId field.");
        }

        if (questionsQuantity <= 0) {
            throw new IllegalArgumentException("Invalid questionsQuantity field.");
        }

        final var quiz = quizRepository
                .findQuizByIdAndUserId(request.getQuizId(), user.getId())
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        var insertedQuestions = quizContentPersister
                .createAndSaveQuizContent(quiz, questionsQuantity, locale);

        quiz.getQuestions().addAll(insertedQuestions);

        return buildQuizDtoResponse(quiz, null);
    }

    @Transactional
    public QuizDtoResponse completeQuizAnswers(EvaluateAnswersRequest request, AuthUser user) {
        final var quizId = request.getQuizId();
        final var userAnswerDtos = request.getUserAnswersDtos();

        if (quizId == null || quizId <= 0) {
            throw new IllegalArgumentException("Invalid quizId field.");
        }

        if (userAnswerDtos == null || userAnswerDtos.isEmpty()) {
            throw new IllegalArgumentException("Invalid answers field.");
        }

        final var quiz = quizRepository
                .findQuizWithQuestionsAndAnswersByIdAndUserId(quizId, user.getId())
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        var isQuizCompleted = quiz.getState().getCode().equals(QuizState.COMPLETED.getCode());

        if (isQuizCompleted) {
            var toEvaluate = evaluatableQuestionAssembler.assemble(quiz.getQuestions());
            var evaluation = quizEvaluationCore.processQuizResult(toEvaluate);
            return buildQuizDtoResponse(quiz, QuizResult.of(evaluation.getResult(), evaluation.getScore()));
        }

        quizStateService.markInProgress(quiz);
        final var questionsById = userAnswerAssembler.questionsById(quiz.getQuestions());

        final var userAnswersByQuestionId = userAnswerAssembler
                .createUserAnswersByQuestionId(request.getUserAnswersDtos(), questionsById);

        userAnswerAssembler.validateAllAnswered(questionsById.keySet(), userAnswersByQuestionId.keySet());
        userAnswerAssembler.applyToQuiz(quiz, userAnswersByQuestionId);
        userAnswerRepository.saveAll(new ArrayList<>(userAnswersByQuestionId.values()));
        quizStateService.markCompleted(quiz);

        var toEvaluate = evaluatableQuestionAssembler.assemble(quiz.getQuestions());
        var evaluation = quizEvaluationCore.processQuizResult(toEvaluate);
        return buildQuizDtoResponse(quiz, QuizResult.of(evaluation.getResult(), evaluation.getScore()));
    }

    private QuizDtoResponse buildQuizDtoResponse(Quiz quiz, QuizResult quizResult) {
        return QuizDtoResponse.builder()
                .quizId(quiz.getId())
                .initialDate(quiz.getInitialDate())
                .endDate(quiz.getEndDate())
                .state(quiz.getState().getDescription())
                .difficultLevel(quiz.getDifficultLevel().getDescription())
                .questions(QuestionMapper.toDto(quiz.getQuestions()))
                .quizResult(quizResult)
                .build();
    }
}