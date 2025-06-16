package com.site.denisalibec.dto;

public class AddToCartRequest {
    private Long productId;
    private String size;
    private int quantity;

    // Getteri si setteri
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
