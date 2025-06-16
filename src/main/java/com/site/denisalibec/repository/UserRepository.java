package com.site.denisalibec.repository;

import com.site.denisalibec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Cauta un user dupa username
    Optional<User> findByUsername(String username);

    // Cauta un user dupa email
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
