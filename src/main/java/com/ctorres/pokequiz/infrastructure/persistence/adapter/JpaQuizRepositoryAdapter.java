package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.QuizRepository;
import com.ctorres.pokequiz.domain.model.Quiz;
import com.ctorres.pokequiz.domain.model.QuizState;
import com.ctorres.pokequiz.infrastructure.mapper.QuizMapper;
import com.ctorres.pokequiz.infrastructure.mapper.StateMapper;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuizEntity;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataQuizRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaQuizRepositoryAdapter implements QuizRepository {

    private final SpringDataQuizRepository repository;
    private final QuizMapper quizMapper;
    private final StateMapper stateMapper;

    public JpaQuizRepositoryAdapter(SpringDataQuizRepository repository) {
        this.repository = repository;
        this.quizMapper = new QuizMapper();
        this.stateMapper = new StateMapper();
    }

    @Override
    public Quiz save(Quiz quiz) {
        QuizEntity saved = repository.save(quizMapper.map(quiz));
        return quizMapper.map(saved);
    }

    @Override
    public Optional<Quiz> findQuizWithQuestionsAndAnswersByIdAndUserId(UUID quizId, UUID userId) {
        Optional<QuizEntity> entity = repository
                .findQuizWithQuestionsAndAnswersByIdAndUserId(quizId, userId);

        return entity.map(quizMapper::map);
    }

    @Override
    public Optional<Quiz> findQuizByIdAndUserId(UUID quizId, UUID userId) {
        Optional<QuizEntity> entity = repository
                .findQuizByIdAndUserId(quizId, userId);

        return entity.map(quizMapper::map);
    }

    @Override
    public int compareAndSetState(UUID quizId, QuizState newState, QuizState previous) {
        return repository.compareAndSetState(quizId, stateMapper.map(newState), stateMapper.map(previous));
    }
}
