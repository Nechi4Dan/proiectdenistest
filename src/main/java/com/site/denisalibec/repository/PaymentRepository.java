package com.site.denisalibec.repository;

import com.site.denisalibec.model.Payment;
import com.site.denisalibec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Gaseste toate platile unui utilizator
    List<Payment> findByUserOrderByCreatedAtDesc(User user);

    // Gaseste platile dupa status
    List<Payment> findByStatus(com.site.denisalibec.enums.PaymentStatus status);
}