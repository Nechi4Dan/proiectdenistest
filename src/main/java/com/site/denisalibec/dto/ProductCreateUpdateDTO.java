package com.site.denisalibec.dto;

import jakarta.validation.constraints.*;

public class ProductCreateUpdateDTO {

    // -------- Variabile --------
    @NotBlank(message = "Numele este obligatoriu")
    @Size(max = 100, message = "Numele nu poate depasi 100 de caractere")
    private String name;

    @NotBlank(message = "Descrierea este obligatorie")
    @Size(max = 1000, message = "Descrierea nu poate depasi 1000 de caractere")
    private String description;

    @Positive(message = "Pretul trebuie sa fie pozitiv")
    private double price;

    @Min(value = 0, message = "Stocul nu poate fi negativ")
    private int stock;

    @NotBlank(message = "Categoria este obligatorie")
    @Size(max = 50, message = "Categoria nu poate depasi 50 de caractere")
    private String category;

    // -------- Constructori --------
    public ProductCreateUpdateDTO() {}

    public ProductCreateUpdateDTO(String name, String description, double price, int stock, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // -------- Getters si Setters --------
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}