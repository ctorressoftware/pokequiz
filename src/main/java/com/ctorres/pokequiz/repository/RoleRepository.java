package com.ctorres.pokequiz.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ctorres.pokequiz.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
