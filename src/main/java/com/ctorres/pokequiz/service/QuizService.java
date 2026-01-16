package com.ctorres.pokequiz.service;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.entity.Answer;
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
    private final QuestionService questionService;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final DifficultLevelRepository difficultLevelRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    public QuizService(
            QuestionService questionService,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            DifficultLevelRepository difficultLevelRepository,
            StateRepository stateRepository,
            UserRepository userRepository) {
        this.questionService = questionService;
        this.difficultLevelRepository = difficultLevelRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
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

        /* Maybe unnecessary
         if (!quiz.getState().getCode().equals(QuizState.CREATED.getCode())) {
            throw new QuizInvalidStateGenerationException(quiz.getId());
        }

        final boolean quizHasQuestions = questionRepository
                .existsByQuiz_IdAndQuiz_User_Id(quiz.getId(), user.getId());

        if (quizHasQuestions) {
            throw new QuizFullContentException(quizId);
        }*/

        claimGenerating(quiz);

        final var generated = questionService.generateQuestions(request.getQuestionsQuantity());

        if (generated.isEmpty()) {
            markGeneratingError(quiz);
            throw new GenerationModuleException("An error ocurred generating questions and answers.");
        }

        var questions = generated.stream().map(gq -> {
            var answers = gq.getGeneratedAnswers().stream()
                    .map(answer -> new Answer(
                            answer.getDescription(),
                            answer.getCanonicalKey(),
                            answer.isCorrect(),
                            true))
                    .collect(Collectors.toSet());

            return new Question(
                    gq.getGeneratedQuestion().getDescription(),
                    true,
                    gq.getGeneratedQuestion().getPokemonFrontImage(),
                    gq.getGeneratedQuestion().getPokemonBackImage(),
                    quiz,
                    answers);
        }).toList();

        var insertedQuestions = questionRepository.saveAll(questions);
        markGenerated(quiz);

        return buildQuizDtoResponse(quiz, insertedQuestions);
    }

    void claimGenerating(Quiz quiz) {
        var created = stateRepository.getReferenceById(QuizState.CREATED.getId());
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generating, created);
        if (claimed == 0) {
            throw new QuizInvalidStateGenerationException(quiz.getId());
        }
    }

    void markGenerated(Quiz quiz) {
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var generated = stateRepository.getReferenceById(QuizState.GENERATED.getId());
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generated, generating);
        if (claimed == 0) {
            throw new QuizInvalidStateTransitionException(quiz.getId());
        }
    }

    void markGeneratingError(Quiz quiz) {
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var generatingError = stateRepository.getReferenceById(QuizState.GENERATING_ERROR.getId());
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generatingError, generating);
        if (claimed == 0) {
            throw new QuizInvalidStateTransitionException(quiz.getId());
        }
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
