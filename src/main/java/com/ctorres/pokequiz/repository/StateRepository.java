package com.ctorres.pokequiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {}