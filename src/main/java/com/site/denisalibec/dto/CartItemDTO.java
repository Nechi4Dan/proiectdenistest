package com.site.denisalibec.dto;

public class CartItemDTO {

    // -------- Variabile --------
    private Long id;
    private Long productId;
    private String productName;
    private double productPrice;
    private String size;
    private int quantity;

    // -------- Constructori --------

    public CartItemDTO() {}

    public CartItemDTO(Long id, Long productId, String productName, double productPrice, String size, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.size = size;
        this.quantity = quantity;
    }

    // -------- Getters si Setters --------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}