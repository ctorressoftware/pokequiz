package com.ctorres.pokequiz.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuestionEntity;

import java.util.UUID;

@Repository
public interface SpringDataQuestionRepository extends JpaRepository<QuestionEntity, UUID> {}