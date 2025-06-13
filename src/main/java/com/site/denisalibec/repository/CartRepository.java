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

    // Găsește coșul asociat unui utilizator
    Optional<Cart> findByUser(User user);
}