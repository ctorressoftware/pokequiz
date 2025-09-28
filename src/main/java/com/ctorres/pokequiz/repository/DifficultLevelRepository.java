package com.ctorres.pokequiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.entity.DifficultLevel;

@Repository
public interface DifficultLevelRepository extends JpaRepository<DifficultLevel, Long> {}