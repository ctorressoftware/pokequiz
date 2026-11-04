package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.StateRepository;
import com.ctorres.pokequiz.domain.model.QuizState;
import com.ctorres.pokequiz.infrastructure.mapper.StateMapper;
import com.ctorres.pokequiz.infrastructure.persistence.entity.StateEntity;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataStateRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JpaStateRepositoryAdapter implements StateRepository {

    private final SpringDataStateRepository repository;
    private final StateMapper stateMapper;

    public JpaStateRepositoryAdapter(SpringDataStateRepository repository) {
        this.repository = repository;
        this.stateMapper = new StateMapper();
    }

    @Override
    public QuizState getReferenceById(UUID id) {
        StateEntity entity = repository.getReferenceById(id);
        return stateMapper.map(entity);
    }
}
