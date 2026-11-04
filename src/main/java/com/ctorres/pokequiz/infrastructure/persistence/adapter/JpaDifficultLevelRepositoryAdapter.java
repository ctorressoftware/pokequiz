package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.DifficultLevelRepository;
import com.ctorres.pokequiz.domain.model.DifficultLevel;
import com.ctorres.pokequiz.infrastructure.mapper.DifficultLevelMapper;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataDifficultLevelRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaDifficultLevelRepositoryAdapter implements DifficultLevelRepository {

    private final SpringDataDifficultLevelRepository repository;
    private final DifficultLevelMapper difficultLevelMapper;

    public JpaDifficultLevelRepositoryAdapter(SpringDataDifficultLevelRepository repository) {
        this.repository = repository;
        this.difficultLevelMapper = new DifficultLevelMapper();
    }

    @Override
    public Optional<DifficultLevel> findById(UUID id) {
        return repository.findById(id).map(difficultLevelMapper::map);
    }
}
