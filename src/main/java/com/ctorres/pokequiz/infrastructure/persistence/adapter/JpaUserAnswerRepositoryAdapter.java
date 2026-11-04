package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.UserAnswerRepository;
import com.ctorres.pokequiz.domain.model.UserAnswer;
import com.ctorres.pokequiz.infrastructure.mapper.UserAnswerMapper;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswerEntity;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataUserAnswerRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JpaUserAnswerRepositoryAdapter implements UserAnswerRepository {

    private final SpringDataUserAnswerRepository repository;
    private final UserAnswerMapper userAnswerMapper;

    public JpaUserAnswerRepositoryAdapter(SpringDataUserAnswerRepository repository) {
        this.repository = repository;
        this.userAnswerMapper = new UserAnswerMapper();
    }

    @Override
    public Collection<UserAnswer> saveAll(Collection<UserAnswer> userAnswers) {

        Collection<UserAnswerEntity> saved = repository.saveAll(userAnswers.stream()
                .map(userAnswerMapper::map)
                .collect(Collectors.toSet()));

        return saved.stream()
                .map(userAnswerMapper::map)
                .collect(Collectors.toSet());
    }
}
