package com.site.denisalibec.dto;

public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String size;
    private int quantity;
    private double price;

    public CartItemDTO(Long id, Long productId, String productName, String size, int quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

    // Getteri si setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
