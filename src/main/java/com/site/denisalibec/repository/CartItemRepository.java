package com.site.denisalibec.repository;

import com.site.denisalibec.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ----------- Interfata pentru acces la datele CartItem ------------------

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // ----------- Metode ------------------

    // Returnează toate produsele dintr-un coș după ID-ul coșului
    List<CartItem> findByCartId(Long cartId);
}