package com.site.denisalibec.repository;

import com.site.denisalibec.model.Payment;
import com.site.denisalibec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ----------- Interfata pentru acces la datele Payment ------------------

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // ----------- Metode ------------------

    // Returnează toate plățile asociate unui utilizator
    List<Payment> findByUser(User user);
}