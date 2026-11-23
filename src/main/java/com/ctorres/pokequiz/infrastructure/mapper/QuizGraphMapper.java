package com.ctorres.pokequiz.infrastructure.mapper;

import com.ctorres.pokequiz.domain.model.Answer;
import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.domain.model.UserAnswer;
import com.ctorres.pokequiz.infrastructure.persistence.entity.AnswerEntity;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuestionEntity;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuizEntity;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswerEntity;

import java.util.HashSet;
import java.util.Set;

final class QuizGraphMapper {
    private final StateMapper stateMapper = new StateMapper();
    private final DifficultLevelMapper difficultLevelMapper = new DifficultLevelMapper();
    private final UserMapper userMapper = new UserMapper();

    Quiz toDomain(QuizEntity entity) {
        Quiz quiz = quizShell(entity);
        for (QuestionEntity questionEntity : entity.getQuestions()) {
            quiz.getQuestions().add(questionToDomain(questionEntity, quiz, true));
        }
        return quiz;
    }

    QuizEntity toEntity(Quiz domain) {
        QuizEntity quiz = quizShell(domain);
        if (domain.getQuestions() != null) {
            for (Question question : domain.getQuestions()) {
                quiz.getQuestions().add(questionToEntity(question, quiz));
            }
        }
        return quiz;
    }

    Question toDomain(QuestionEntity entity) {
        Quiz quiz = quizShell(entity.getQuiz());
        Question question = questionToDomain(entity, quiz, true);
        quiz.getQuestions().add(question);
        return question;
    }

    QuestionEntity toEntity(Question domain) {
        return questionToEntity(domain, quizShell(domain.quiz()));
    }

    Answer toDomain(AnswerEntity entity) {
        QuestionEntity questionEntity = entity.getQuestion();
        Quiz quiz = quizShell(questionEntity.getQuiz());
        Question question = questionToDomain(questionEntity, quiz, false);
        Answer answer = answerToDomain(entity, question);
        question.answers().add(answer);
        quiz.getQuestions().add(question);
        return answer;
    }

    AnswerEntity toEntity(Answer domain) {
        return answerToEntity(domain, questionShell(domain.getQuestion()));
    }

    UserAnswer toDomain(UserAnswerEntity entity) {
        QuestionEntity questionEntity = entity.getQuestion();
        Quiz quiz = quizShell(questionEntity.getQuiz());
        Question question = questionToDomain(questionEntity, quiz, false);
        UserAnswer userAnswer = userAnswerToDomain(entity, question);
        question.userAnswers().add(userAnswer);
        quiz.getQuestions().add(question);
        return userAnswer;
    }

    UserAnswerEntity toEntity(UserAnswer domain) {
        return userAnswerToEntity(domain, questionShell(domain.getQuestion()));
    }

    private Quiz quizShell(QuizEntity entity) {
        return Quiz.restore(
                entity.getId(),
                entity.getInitialDate(),
                entity.getEndDate(),
                stateMapper.map(entity.getState()),
                difficultLevelMapper.map(entity.getDifficultLevel()),
                userMapper.map(entity.getUser()),
                new HashSet<>()
        );
    }

    private QuizEntity quizShell(Quiz domain) {
        return new QuizEntity(
                domain.getId(),
                domain.getStartDate(),
                domain.getEndDate(),
                stateMapper.map(domain.getState()),
                difficultLevelMapper.map(domain.getDifficultLevel()),
                userMapper.map(domain.getUser())
        );
    }

    private Question questionToDomain(QuestionEntity entity, Quiz quiz, boolean includeAnswers) {
        Set<Answer> answers = new HashSet<>();
        Set<UserAnswer> userAnswers = new HashSet<>();
        Question question = Question.restore(
                entity.getId(),
                entity.getDescription(),
                entity.isActive(),
                entity.getFrontImageUrl(),
                entity.getBackImageUrl(),
                quiz,
                answers,
                userAnswers
        );
        if (includeAnswers) {
            for (AnswerEntity answerEntity : entity.getAnswers()) {
                answers.add(answerToDomain(answerEntity, question));
            }
            for (UserAnswerEntity userAnswerEntity : entity.getUserAnswers()) {
                userAnswers.add(userAnswerToDomain(userAnswerEntity, question));
            }
        }
        return question;
    }

    private QuestionEntity questionShell(Question domain) {
        return new QuestionEntity(
                domain.id(),
                domain.description(),
                domain.active(),
                domain.frontImageUrl(),
                domain.backImageUrl(),
                quizShell(domain.quiz()),
                Set.of(),
                Set.of()
        );
    }

    private QuestionEntity questionToEntity(Question domain, QuizEntity quiz) {
        Set<AnswerEntity> answers = new HashSet<>();
        Set<UserAnswerEntity> userAnswers = new HashSet<>();
        if (domain.answers() != null) {
            for (Answer answer : domain.answers()) {
                answers.add(answerToEntity(answer, null));
            }
        }
        if (domain.userAnswers() != null) {
            for (UserAnswer userAnswer : domain.userAnswers()) {
                userAnswers.add(userAnswerToEntity(userAnswer, null));
            }
        }
        return new QuestionEntity(
                domain.id(),
                domain.description(),
                domain.active(),
                domain.frontImageUrl(),
                domain.backImageUrl(),
                quiz,
                answers,
                userAnswers
        );
    }

    private Answer answerToDomain(AnswerEntity entity, Question question) {
        return Answer.restore(
                entity.getId(),
                entity.getDescription(),
                entity.getCanonicalKey(),
                entity.isCorrect(),
                entity.isActive(),
                question
        );
    }

    private AnswerEntity answerToEntity(Answer domain, QuestionEntity question) {
        return new AnswerEntity(
                domain.getId(),
                domain.getDescription(),
                domain.getCanonicalKey(),
                domain.isCorrect(),
                domain.isActive(),
                question
        );
    }

    private UserAnswer userAnswerToDomain(UserAnswerEntity entity, Question question) {
        return new UserAnswer(
                entity.getId(),
                entity.getDescription(),
                entity.getCanonicalKey(),
                entity.isActive(),
                question
        );
    }

    private UserAnswerEntity userAnswerToEntity(UserAnswer domain, QuestionEntity question) {
        return new UserAnswerEntity(
                domain.getId(),
                domain.getDescription(),
                domain.getCanonicalKey(),
                domain.isActive(),
                question
        );
    }
}
