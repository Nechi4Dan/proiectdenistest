package com.site.denisalibec.repository;

import com.site.denisalibec.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// ----------- Interfata pentru acces la datele ProductStock ------------------

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    // ----------- Metode ------------------

    // Returneaza toate intrarile de stoc pentru un anumit produs
    List<ProductStock> findByProductId(Long productId);
}