package com.ctorres.pokequiz.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.infrastructure.persistence.entity.DifficultLevel;

@Repository
public interface DifficultLevelRepository extends JpaRepository<DifficultLevel, Long> {}