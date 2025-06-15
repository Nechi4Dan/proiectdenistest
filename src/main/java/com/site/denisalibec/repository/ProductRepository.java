package com.site.denisalibec.repository;

import com.site.denisalibec.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
