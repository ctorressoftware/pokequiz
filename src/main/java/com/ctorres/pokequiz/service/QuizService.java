package com.ctorres.pokequiz.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.GenerateQuizContentResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.entity.Answer;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.StateOption;
import com.ctorres.pokequiz.exception.BadRequestException;
import com.ctorres.pokequiz.exception.DifficultLevelNotFoundException;
import com.ctorres.pokequiz.exception.GenerationModuleException;
import com.ctorres.pokequiz.exception.QuizNotFoundException;
import com.ctorres.pokequiz.repository.*;
import com.ctorres.pokequiz.service.security.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizService {
    private final QuestionService questionService;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DifficultLevelRepository difficultLevelRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    public QuizService(
            QuestionService questionService,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            DifficultLevelRepository difficultLevelRepository,
            StateRepository stateRepository,
            UserRepository userRepository) {
        this.questionService = questionService;
        this.difficultLevelRepository = difficultLevelRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
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

    public QuizDtoResponse getQuizById(Long quizId, AuthUser authenticatedUser) {

        if (quizId == null || quizId == 0) {
            throw new BadRequestException("Invalid quizId field.");
        }

        var optionalQuiz = quizRepository.findById(quizId);

        if (optionalQuiz.isEmpty()) {
            throw new QuizNotFoundException(quizId);
        }

        var quiz = optionalQuiz.get();

        return QuizDtoResponse.builder()
                .quizId(quiz.getId())
                .initialDate(quiz.getInitialDate())
                .endDate(quiz.getEndDate())
                .state(quiz.getState().getDescription())
                .difficultLevel(quiz.getDifficultLevel().getDescription())
                .build();
    }

    @Transactional
    public GenerateQuizContentResponse generateAndSaveContent(GenerateQuizContentRequest request, AuthUser user) {
        // TODO validate quizId vs user
        final Optional<Long> quizId = Optional.ofNullable(request.getQuizId());
        final int questionsQuantity = request.getQuestionsQuantity();

        if (quizId.isEmpty()) {
            throw new BadRequestException("Invalid quizId field.");
        }

        if (questionsQuantity <= 0) {
            throw new BadRequestException("Invalid questionsQuantity field.");
        }

        var questions = questionService.generateQuestions(request.getQuestionsQuantity());

        if (questions.isEmpty()) {
            throw new GenerationModuleException("An error ocurred generating questions and answers.");
        }

        for (var generatedQuestion : questions) {

            var insertedQuestion = questionRepository.save(new Question(
                    generatedQuestion.getGeneratedQuestion().getDescription(),
                    true,
                    quizRepository.getReferenceById(quizId.get())
            ));

            List<Answer> answerList = new ArrayList<>();

            for (var generatedAnswer : generatedQuestion.getGeneratedAnswers()) {

                answerList.add(new Answer(
                        generatedAnswer.getDescription(),
                        generatedAnswer.getCanonicalKey(),
                        generatedAnswer.isCorrect(),
                        true,
                        insertedQuestion
                ));
            }

            answerRepository.saveAll(answerList);
        }

        return new GenerateQuizContentResponse(
                request.getQuizId(),
                request.getQuestionsQuantity(),
                questions
        );
    }
}
