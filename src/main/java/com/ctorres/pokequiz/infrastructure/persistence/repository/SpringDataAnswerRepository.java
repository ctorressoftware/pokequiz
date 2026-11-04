package com.ctorres.pokequiz.infrastructure.persistence.repository;

import com.ctorres.pokequiz.infrastructure.persistence.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {}