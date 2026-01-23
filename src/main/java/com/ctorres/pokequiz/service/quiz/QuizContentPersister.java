package com.ctorres.pokequiz.service.quiz;

import com.ctorres.pokequiz.entity.Answer;
import com.ctorres.pokequiz.entity.Question;
import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.exception.GenerationModuleException;
import com.ctorres.pokequiz.repository.QuestionRepository;
import com.ctorres.pokequiz.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizContentPersister {
    private final QuestionService questionService;
    private final QuizStateService quizStateService;
    private final QuestionRepository questionRepository;

    public QuizContentPersister(
            QuestionService questionService,
            QuizStateService quizStateService,
            QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.quizStateService = quizStateService;
        this.questionRepository = questionRepository;
    }

    private List<Question> createQuizContent(Quiz quiz, int questionsQuantity) {

        final var generated = questionService.generateQuestions(questionsQuantity);

        if (generated.isEmpty()) {
            throw new GenerationModuleException("An error ocurred generating questions and answers.");
        }

        return generated.stream().map(gq -> {
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
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Question> createAndSaveQuizContent(Quiz quiz, int questionsQuantity) {
        quizStateService.claimGenerating(quiz);
        try {
            var questions = createQuizContent(quiz, questionsQuantity);
            var inserted = questionRepository.saveAll(questions);
            quizStateService.markReady(quiz);
            return inserted;
        } catch (RuntimeException e) {
            quizStateService.markGeneratingError(quiz);
            throw e;
        }
    }
}
