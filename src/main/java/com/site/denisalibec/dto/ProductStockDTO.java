package com.site.denisalibec.dto;

// DTO pentru o varianta de stoc (ex: produs marime M, 10 bucati)

// TODO: Acest DTO va fi folosit cand se adauga functionalitati de CRUD pentru variante de stoc (ex. marimi)

public class ProductStockDTO {
    private Long id;
    private Long productId; // ID-ul produsului asociat
    private String size;
    private int stock;

    // Constructor gol
    public ProductStockDTO() {}

    // Constructor complet
    public ProductStockDTO(Long id, Long productId, String size, int stock) {
        this.id = id;
        this.productId = productId;
        this.size = size;
        this.stock = stock;
    }

    // Getteri si setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

