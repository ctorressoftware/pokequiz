package com.ctorres.pokequiz.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.ctorres.pokequiz.dto.api.request.CreateQuizRequest;
import com.ctorres.pokequiz.dto.api.request.GenerateQuizContentRequest;
import com.ctorres.pokequiz.dto.api.response.CreateQuizResponse;
import com.ctorres.pokequiz.dto.api.response.GenerateQuizContentResponse;
import com.ctorres.pokequiz.dto.api.response.QuizDtoResponse;
import com.ctorres.pokequiz.entity.Answer;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.StateOption;
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

    @Transactional
    public CreateQuizResponse createQuiz(CreateQuizRequest request, AuthUser authenticatedUser) {
        final var user = userRepository.getReferenceById(authenticatedUser.getId());
        final var state = stateRepository.getReferenceById(StateOption.CREATED.getId());
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

    public QuizDtoResponse getQuizById(Long quizId, AuthUser authenticatedUser) {

        if (quizId == null || quizId <= 0) {
            throw new BadRequestException("Invalid quizId field.");
        }

        final var optionalQuiz = quizRepository.findQuizWithQuestionsAndAnswers(quizId);

        if (optionalQuiz.isEmpty()) {
            throw new QuizNotFoundException(quizId);
        }

        final var quiz = optionalQuiz.get();

        final boolean isSameUser = quiz.getUser().getId().equals(authenticatedUser.getId());

        if (!isSameUser) {
            throw new QuizNotFoundException(quizId);
        }

        return QuizDtoResponse.builder()
                .quizId(quiz.getId())
                .initialDate(quiz.getInitialDate())
                .endDate(quiz.getEndDate())
                .state(quiz.getState().getDescription())
                .difficultLevel(quiz.getDifficultLevel().getDescription())
                .questions(QuestionMapper.toDto(quiz.getQuestions()))
                .build();
    }

    @Transactional
    public GenerateQuizContentResponse generateAndSaveContent(GenerateQuizContentRequest request, AuthUser user) {

        final int questionsQuantity = request.getQuestionsQuantity();

        if (questionsQuantity <= 0) {
            throw new BadRequestException("Invalid questionsQuantity field.");
        }

        final var optionalQuiz = quizRepository.findQuizWithQuestionsAndAnswers(request.getQuizId());

        if (optionalQuiz.isEmpty()) {
            throw new BadRequestException("Invalid quizId field.");
        }

        final var quiz = optionalQuiz.get();

        final boolean isSameUser = quiz.getUser().getId().equals(user.getId());

        if (!isSameUser) {
            throw new QuizNotFoundException(quiz.getId());
        }

        if (!quiz.getUser().isActive()) {
            throw new InactiveUserException();
        }

        if (!quiz.getQuestions().isEmpty()) {
            throw new QuizFullContentException(quiz.getId());
        }

        final var questions = questionService.generateQuestions(request.getQuestionsQuantity());

        if (questions.isEmpty()) {
            throw new GenerationModuleException("An error ocurred generating questions and answers.");
        }

        for (var generatedQuestion : questions) {

            var insertedQuestion = questionRepository.save(new Question(
                    generatedQuestion.getGeneratedQuestion().getDescription(),
                    true,
                    quiz
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
