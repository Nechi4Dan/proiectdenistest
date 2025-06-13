package com.site.denisalibec.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemCreateUpdateDTO {

    // ----------- Variabile ------------------

    @NotNull(message = "Cart ID is required")
    private Long cartId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    private String size;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    // ----------- Constructori ------------------

    public CartItemCreateUpdateDTO() {}

    public CartItemCreateUpdateDTO(Long cartId, Long productId, String size, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.size = size;
        this.quantity = quantity;
    }

    // ----------- Getters si Setters ------------------

    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}