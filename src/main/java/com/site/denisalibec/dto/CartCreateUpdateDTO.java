package com.site.denisalibec.dto;

import jakarta.validation.constraints.NotNull;

public class CartCreateUpdateDTO {

    // -------- Variabile --------
    @NotNull(message = "User ID este obligatoriu")
    private Long userId;

    // -------- Constructori --------

    public CartCreateUpdateDTO() {}

    public CartCreateUpdateDTO(Long userId) {
        this.userId = userId;
    }

    // -------- Getters si Setters --------

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}