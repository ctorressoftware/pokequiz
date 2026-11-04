package com.ctorres.pokequiz.infrastructure.persistence.repository;

import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataUserAnswerRepository extends JpaRepository<UserAnswerEntity, UUID> {}