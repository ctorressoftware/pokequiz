package com.ctorres.pokequiz.application.usecase.core.quiz;

import java.util.*;

import com.ctorres.pokequiz.application.port.in.quiz.complete.CompleteQuizCommand;
import com.ctorres.pokequiz.application.port.in.quiz.complete.CompleteQuizResult;
import com.ctorres.pokequiz.application.port.in.quiz.complete.CompleteQuizUseCase;
import com.ctorres.pokequiz.application.port.in.quiz.create.CreateQuizCommand;
import com.ctorres.pokequiz.application.port.in.quiz.create.CreateQuizResult;
import com.ctorres.pokequiz.application.port.in.quiz.create.CreateQuizUseCase;
import com.ctorres.pokequiz.application.port.in.quiz.generate.GenerateQuizCommand;
import com.ctorres.pokequiz.application.port.in.quiz.generate.GenerateQuizResult;
import com.ctorres.pokequiz.application.port.in.quiz.generate.GenerateQuizUseCase;
import com.ctorres.pokequiz.application.port.in.quiz.getById.GetQuizByIdCommand;
import com.ctorres.pokequiz.application.port.in.quiz.getById.GetQuizByIdResult;
import com.ctorres.pokequiz.application.port.in.quiz.getById.GetQuizByIdUseCase;
import com.ctorres.pokequiz.application.port.out.*;
import com.ctorres.pokequiz.application.usecase.core.QuizResult;
import com.ctorres.pokequiz.domain.exception.DifficultLevelNotFoundException;
import com.ctorres.pokequiz.domain.exception.QuizNotFoundException;
import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.infrastructure.persistence.repository.*;
import com.ctorres.pokequiz.application.usecase.core.quiz.evaluation.EvaluatableQuestionAssembler;
import com.ctorres.pokequiz.application.usecase.core.quiz.evaluation.QuizEvaluationCore;
import com.ctorres.pokequiz.application.usecase.core.quiz.state.QuizStateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizOrchestrator
        implements CreateQuizUseCase, GetQuizByIdUseCase, GenerateQuizUseCase, CompleteQuizUseCase {
    private final EvaluatableQuestionAssembler evaluatableQuestionAssembler;
    private final QuizContentPersister quizContentPersister;
    private final QuizEvaluationCore quizEvaluationCore;
    private final QuizStateService quizStateService;
    private final UserAnswerAssembler userAnswerAssembler;
    private final UserAnswerRepository userAnswerRepository;
    private final QuizRepository quizRepository;
    private final DifficultLevelRepository difficultLevelRepository;
    private final UserRepository userRepository;
    private final StateRepository stateRepository;

    public QuizOrchestrator(
            EvaluatableQuestionAssembler evaluatableQuestionAssembler,
            QuizEvaluationCore quizEvaluationCore,
            QuizContentPersister quizContentPersister,
            QuizStateService quizStateService,
            UserAnswerAssembler userAnswerAssembler,
            UserAnswerRepository userAnswerRepository,
            QuizRepository quizRepository,
            DifficultLevelRepository difficultLevelRepository,
            UserRepository userRepository,
            StateRepository stateRepository) {
        this.evaluatableQuestionAssembler = evaluatableQuestionAssembler;
        this.quizEvaluationCore = quizEvaluationCore;
        this.quizContentPersister = quizContentPersister;
        this.quizStateService = quizStateService;
        this.userAnswerAssembler = userAnswerAssembler;
        this.userAnswerRepository = userAnswerRepository;
        this.quizRepository = quizRepository;
        this.difficultLevelRepository = difficultLevelRepository;
        this.userRepository = userRepository;
        this.stateRepository = stateRepository;
    }

    @Transactional
    public CreateQuizResult createQuiz(CreateQuizCommand command) {
        var user = userRepository.getReferenceById(command.user().id());
        var state = stateRepository.getReferenceById(QuizState.CREATED.getId());
        var difficult = difficultLevelRepository.findById(command.difficultLevelId())
                .orElseThrow(() -> new DifficultLevelNotFoundException(command.difficultLevelId()));
        var quiz = Quiz.create(state, difficult, user);

        var inserted = quizRepository.save(quiz);
        return new CreateQuizResult(inserted.getId());
    }

    public GetQuizByIdResult getQuizById(GetQuizByIdCommand command) {

        if (command.quizId() == null) {
            throw new IllegalArgumentException("Invalid quizId field.");
        }

        final var quiz = quizRepository
                .findQuizWithQuestionsAndAnswersByIdAndUserId(command.quizId(), command.user().id())
                .orElseThrow(() -> new QuizNotFoundException(command.quizId()));

        if (quiz.getState().code().equals(QuizState.COMPLETED.getCode())) {
            var toEvaluate = evaluatableQuestionAssembler.assemble(quiz.getQuestions());
            var evaluation = quizEvaluationCore.processQuizResult(toEvaluate);

            return new GetQuizByIdResult(
                    quiz,
                    QuizResult.of(evaluation.getResult(), evaluation.getScore())
            );
        }

        return new GetQuizByIdResult(quiz, null);
    }

    @Transactional
    public GenerateQuizResult generateAndSaveContent(GenerateQuizCommand command) {
        final UUID quizId = command.quizId();
        final int questionsQuantity = command.questionsQuantity();

        if (quizId == null) {
            throw new IllegalArgumentException("Invalid quizId field.");
        }

        if (questionsQuantity <= 0) {
            throw new IllegalArgumentException("Invalid questionsQuantity field.");
        }

        final var quiz = quizRepository
                .findQuizByIdAndUserId(command.quizId(), command.user().id())
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        var insertedQuestions = quizContentPersister
                .createAndSaveQuizContent(quiz, questionsQuantity, command.locale());

        quiz.getQuestions().addAll(insertedQuestions);

        return new GenerateQuizResult(quiz, null);
    }

    @Transactional
    public CompleteQuizResult completeQuizAnswers(CompleteQuizCommand command) {
        final var quizId = command.quizId();
        final var userAnswerDtos = command.userAnswers();

        if (quizId == null) {
            throw new IllegalArgumentException("Invalid quizId field.");
        }

        if (userAnswerDtos == null || userAnswerDtos.isEmpty()) {
            throw new IllegalArgumentException("Invalid answers field.");
        }

        final var quiz = quizRepository
                .findQuizWithQuestionsAndAnswersByIdAndUserId(quizId, command.user().id())
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        var isQuizCompleted = quiz.getState().code().equals(QuizState.COMPLETED.getCode());

        if (isQuizCompleted) {
            var toEvaluate = evaluatableQuestionAssembler.assemble(quiz.getQuestions());
            var evaluation = quizEvaluationCore.processQuizResult(toEvaluate);
            return new CompleteQuizResult(quiz, QuizResult.of(evaluation.getResult(), evaluation.getScore()));
        }

        quizStateService.markInProgress(quiz);
        final var questionsById = userAnswerAssembler.questionsById(quiz.getQuestions());

        final var userAnswersByQuestionId = userAnswerAssembler
                .createUserAnswersByQuestionId(command.userAnswers(), questionsById);

        userAnswerAssembler.validateAllAnswered(questionsById.keySet(), userAnswersByQuestionId.keySet());
        userAnswerAssembler.applyToQuiz(quiz, userAnswersByQuestionId);
        userAnswerRepository.saveAll(new ArrayList<>(userAnswersByQuestionId.values()));
        quizStateService.markCompleted(quiz);

        var toEvaluate = evaluatableQuestionAssembler.assemble(quiz.getQuestions());
        var evaluation = quizEvaluationCore.processQuizResult(toEvaluate);
        return new CompleteQuizResult(quiz, QuizResult.of(evaluation.getResult(), evaluation.getScore()));
    }
}