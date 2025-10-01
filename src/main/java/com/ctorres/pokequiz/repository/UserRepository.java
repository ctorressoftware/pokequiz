package com.ctorres.pokequiz.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ctorres.pokequiz.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndActiveTrue(String username);

    boolean existsByUsername(String username);
}