package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.UserRepository;
import com.ctorres.pokequiz.domain.model.auth.User;
import com.ctorres.pokequiz.infrastructure.mapper.UserMapper;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserEntity;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JpaUserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository repository;
    private final UserMapper userMapper;

    public JpaUserRepositoryAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
        this.userMapper = new UserMapper();
    }

    @Override
    public User getReferenceById(UUID id) {
        UserEntity entity = repository.getReferenceById(id);
        return userMapper.map(entity);
    }

    @Override
    public User getByUsername(String username) {
        UserEntity entity = repository.getByUsername(username);
        return userMapper.map(entity);
    }

    @Override
    public User getById(UUID id) {
        UserEntity entity = repository.getById(id);
        return userMapper.map(entity);
    }

    @Override
    public User save(User user) {
        UserEntity entity = repository.save(userMapper.map(user));
        return userMapper.map(entity);
    }
}
