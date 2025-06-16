package com.site.denisalibec.repository;

import com.site.denisalibec.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    // TODO: Va fi util cand vom afisa stocurile in functie de produs
    List<ProductStock> findByProductId(Long productId);
}
