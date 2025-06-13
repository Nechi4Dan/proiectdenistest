package com.site.denisalibec.repository;

import com.site.denisalibec.model.Receipt;
import com.site.denisalibec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ----------- Interfata pentru acces la datele Receipt ------------------

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    // ----------- Metode ------------------

    // Returnează toate chitanțele asociate unui utilizator (prin Payment)
    List<Receipt> findAllByPayment_User(User user);
}