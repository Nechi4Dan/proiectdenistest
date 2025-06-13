package com.site.denisalibec.repository;

import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// ----------- Interfata pentru acces la datele Cart ------------------

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // ----------- Metode ------------------

    // Gaseste cosul asociat unui utilizator
    Optional<Cart> findByUser(User user);

    // Gaseste cosul asociat unui utilizator pe baza ID-ului utilizatorului
    Optional<Cart> findByUserId(Long userId);
}
