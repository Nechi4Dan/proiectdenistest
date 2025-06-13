package com.site.denisalibec.repository;

import com.site.denisalibec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// ----------- Interfata pentru acces la datele User ------------------

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ----------- Metode ------------------

    // Cauta utilizator dupa email
    Optional<User> findByEmail(String email);

    // Cauta utilizator dupa username
    Optional<User> findByUsername(String username);

    // Verifica existenta unui utilizator dupa email
    boolean existsByEmail(String email);

    // Verifica existenta unui utilizator dupa username
    boolean existsByUsername(String username);
}