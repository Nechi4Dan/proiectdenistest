package com.site.denisalibec.dto;

public class ProductStockDTO {

    // -------- Variabile --------
    private Long id;           // ID-ul variantei de stoc
    private String size;       // Marimea (ex: S, M, L)
    private int stock;         // Cantitatea disponibila pe aceasta marime
    private Long productId;    // ID-ul produsului asociat

    // -------- Constructori --------

    public ProductStockDTO() {}

    public ProductStockDTO(Long id, String size, int stock, Long productId) {
        this.id = id;
        this.size = size;
        this.stock = stock;
        this.productId = productId;
    }

    // -------- Getters si Setters --------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}