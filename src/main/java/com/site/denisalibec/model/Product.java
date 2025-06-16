package com.site.denisalibec.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

// ----------- Entitate pentru produs ------------------

@Entity
@Table(name = "products")
public class Product {

    // ----------- Variabile ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele produsului este obligatoriu")
    private String name;

    @NotBlank(message = "Descrierea este obligatorie")
    private String description;

    @Positive(message = "Pretul trebuie sa fie pozitiv")
    private double price;

    @Min(value = 0, message = "Stocul nu poate fi negativ")
    private int stock;

    @NotBlank(message = "Categoria este obligatorie")
    private String category;

    // TODO: momentan nefolosit in front - tine variante de stoc ( ex marimi)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductStock> stockVariants = new ArrayList<>();

    private String image;

    // ----------- Constructori ------------------
    public Product() {}

    public Product(String name, String description, double price, int stock, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // TODO: Acest constructor include stockVariants si imagine dar nu e folosit acum.
    public Product(Long id, String name, String description, double price, int stock, String category, List<ProductStock> stockVariants, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.stockVariants = stockVariants;
        this.image = image;
    }

    // ----------- Getteri si Setteri ------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    // TODO: Acest camp nu este inca folosit in front/back. se va folosi pentru variante de stoc (ex marimi)
    public List<ProductStock> getStockVariants() { return stockVariants; }
    public void setStockVariants(List<ProductStock> stockVariants) { this.stockVariants = stockVariants; }

    public String getImage() { return image; }
    public void setImage(String image){ this.image = image; }
}
