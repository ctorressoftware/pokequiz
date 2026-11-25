package com.ctorres.pokequiz.application.usecase.quiz.persistence;

import com.ctorres.pokequiz.application.port.out.QuestionRepository;
import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.domain.model.Quiz;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
public class QuestionPersister {
    private final QuizStateService quizStateService;
    private final QuestionRepository questionRepository;

    public QuestionPersister(
            QuizStateService quizStateService,
            QuestionRepository questionRepository) {
        this.quizStateService = quizStateService;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Collection<Question> save(Quiz quiz, Collection<Question> questions) {
        Collection<Question> saved = questionRepository.saveAll(questions);
        quizStateService.markReady(quiz);
        return saved;
    }
}
