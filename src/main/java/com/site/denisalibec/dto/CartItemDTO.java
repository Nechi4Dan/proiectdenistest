package com.site.denisalibec.dto;

public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String size;
    private int quantity;
    private double price;
    private String image; // ADAUGAT pentru imaginea produsului

    public CartItemDTO(Long id, Long productId, String productName, String size, int quantity, double price, String image) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
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

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}