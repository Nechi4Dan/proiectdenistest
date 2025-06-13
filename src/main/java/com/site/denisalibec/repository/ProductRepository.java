package com.site.denisalibec.repository;

import com.site.denisalibec.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// ----------- Interfata pentru acces la datele Product ------------------

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ----------- Metode ------------------

    // Filtrare dupa categorie
    Page<Product> findByCategory(String category, Pageable pageable);

    // Cautare generala dupa nume, descriere sau categorie
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> searchProducts(String search, Pageable pageable);
}