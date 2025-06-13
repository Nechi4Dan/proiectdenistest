package com.site.denisalibec.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    // ----------- Variabile ------------------
    private Long id;

    @NotBlank(message = "Numele este obligatoriu.")
    @Size(max = 100, message = "Numele nu poate avea mai mult de 100 de caractere.")
    private String name;

    @Size(max = 255, message = "Descrierea nu poate avea mai mult de 255 de caractere.")
    private String description;

    @NotNull(message = "Pretul este obligatoriu.")
    @Min(value = 0, message = "Pretul trebuie sa fie pozitiv.")
    private Double price;

    @Min(value = 0, message = "Stocul nu poate fi negativ.")
    private int stock;

    @NotBlank(message = "Categoria este obligatorie.")
    private String category;

    // ----------- Constructori ------------------
    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, Double price, int stock, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // ----------- Getteri si Setteri ------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}