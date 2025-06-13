package com.site.denisalibec.dto;

import jakarta.validation.constraints.*;

public class ProductStockCreateUpdateDTO {

    // -------- Variabile --------
    @NotBlank(message = "Marimea este obligatorie")
    private String size;

    @Min(value = 0, message = "Stocul trebuie sa fie >= 0")
    private int stock;

    // -------- Constructori --------

    public ProductStockCreateUpdateDTO() {}

    public ProductStockCreateUpdateDTO(String size, int stock) {
        this.size = size;
        this.stock = stock;
    }

    // -------- Getters si Setters --------

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}