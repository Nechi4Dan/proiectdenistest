package com.site.denisalibec.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// ----------- Entitate pentru stocurile variantelor de produs ------------------

@Entity
@Table(name = "product_stock")
public class ProductStock {

    // ----------- Variabile ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotBlank(message = "Marimea este obligatorie")
    private String size;

    @Min(value = 0, message = "Stocul nu poate fi negativ")
    private int stock;

    // ----------- Constructori ------------------
    public ProductStock() {}

    public ProductStock(Product product, String size, int stock) {
        this.product = product;
        this.size = size;
        this.stock = stock;
    }

    public ProductStock(Long id, Product product, String size, int stock) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.stock = stock;
    }

    // ----------- Getteri si Setteri ------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}