package com.ctorres.pokequiz.application.usecase.quiz.persistence;

import com.ctorres.pokequiz.domain.model.Answer;
import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.application.usecase.quiz.exception.GenerationModuleException;
import com.ctorres.pokequiz.application.usecase.quiz.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class QuizContentPersister {
    private final QuestionService questionService;
    private final QuizStateService quizStateService;
    private final QuestionPersister questionPersister;

    public QuizContentPersister(
            QuestionService questionService,
            QuizStateService quizStateService,
            QuestionPersister questionPersister) {
        this.questionService = questionService;
        this.quizStateService = quizStateService;
        this.questionPersister = questionPersister;
    }

    private List<Question> createQuizContent(Quiz quiz, int questionsQuantity, Locale locale) {

        final var generated = questionService.generateQuestions(questionsQuantity, locale);

        if (generated.isEmpty()) {
            throw new GenerationModuleException("An error occurred generating questions and answers.");
        }

        return generated.stream().map(item -> {

            Question question = Question.create(
                    item.question().description(),
                    true,
                    item.question().pokemonFrontImage(),
                    item.question().pokemonBackImage(),
                    quiz,
                    new HashSet<>(),
                    new HashSet<>()
            );

            var answers = item.answers().stream()
                    .map(answer -> Answer.create(
                            answer.description(),
                            answer.canonicalKey(),
                            answer.correct(),
                            true,
                            question
                    ))
                    .collect(Collectors.toSet());

            question.answers().addAll(answers);
            return question;

        }).toList();
    }

    public Collection<Question> createAndSaveQuizContent(Quiz quiz, int questionsQuantity, Locale locale) {
        quizStateService.claimGenerating(quiz);
        try {
            Collection<Question> questions = createQuizContent(quiz, questionsQuantity, locale);
            return questionPersister.save(quiz, questions);
        } catch (RuntimeException e) {
            quizStateService.markGeneratingError(quiz);
            throw e;
        }
    }
}
