package com.site.denisalibec.repository;

import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    void deleteByUser(User user);
}

