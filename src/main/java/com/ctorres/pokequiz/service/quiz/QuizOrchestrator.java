package com.ctorres.pokequiz.service.quiz;

import java.time.Instant;
import java.util.*;

import com.ctorres.pokequiz.dto.api.QuizResult;
import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.EvaluateAnswersRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.QuizState;
import com.ctorres.pokequiz.exception.*;
import com.ctorres.pokequiz.mapper.QuestionMapper;
import com.ctorres.pokequiz.repository.*;
import com.ctorres.pokequiz.service.quiz.evaluation.EvaluatableQuestionAssembler;
import com.ctorres.pokequiz.service.quiz.evaluation.QuizEvaluationCore;
import com.ctorres.pokequiz.service.quiz.state.QuizStateService;
import com.ctorres.pokequiz.service.security.AuthUser;
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
            throw new BadRequestException("Invalid quizId field.");
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
            throw new BadRequestException("Invalid quizId field.");
        }

        if (questionsQuantity <= 0) {
            throw new BadRequestException("Invalid questionsQuantity field.");
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
            throw new BadRequestException("Invalid quizId field.");
        }

        if (userAnswerDtos == null || userAnswerDtos.isEmpty()) {
            throw new BadRequestException("Invalid answers field.");
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