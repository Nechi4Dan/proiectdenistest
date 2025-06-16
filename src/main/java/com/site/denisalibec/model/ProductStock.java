package com.site.denisalibec.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// ----------- Entitate pentru stocurile variantelor de produs ------------------

@Entity
@Table(name = "product_stock")
public class ProductStock {

    // ----------- Variabile ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Acest camp este folosit pentru a lega o varianta de stoc (ex: marime) de un produs.
    // Momentan nu este folosit activ in frontend, dar va fi util cand adaugam functionalitati pentru variante (ex: marimi, culori).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotBlank(message = "Marimea este obligatorie")
    private String size;

    @Min(value = 0, message = "Stocul nu poate fi negativ")
    private int stock;

    // ----------- Constructori ------------------

    public ProductStock() {
    }

    // TODO: Folosit in viitor cand vom crea sau edita variante de produs (ex: marime M, L etc.)
    public ProductStock(Product product, String size, int stock) {
        this.product = product;
        this.size = size;
        this.stock = stock;
    }

    // TODO: Constructor complet - momentan nefolosit, dar util pentru operatiuni CRUD
    public ProductStock(Long id, Product product, String size, int stock) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.stock = stock;
    }

    // ----------- Getteri si Setteri ------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
