package com.ctorres.pokequiz.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.infrastructure.persistence.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {}