package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.DifficultLevelRepository;
import com.ctorres.pokequiz.application.port.out.RoleRepository;
import com.ctorres.pokequiz.domain.model.DifficultLevel;
import com.ctorres.pokequiz.domain.model.auth.Role;
import com.ctorres.pokequiz.infrastructure.mapper.DifficultLevelMapper;
import com.ctorres.pokequiz.infrastructure.mapper.RoleMapper;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataDifficultLevelRepository;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaRoleRepositoryAdapter implements RoleRepository {

    private final SpringDataRoleRepository repository;
    private final RoleMapper mapper;

    public JpaRoleRepositoryAdapter(SpringDataRoleRepository repository) {
        this.repository = repository;
        this.mapper = new RoleMapper();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name).map(mapper::map);
    }
}
