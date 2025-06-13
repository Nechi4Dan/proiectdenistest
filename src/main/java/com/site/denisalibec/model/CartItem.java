package com.site.denisalibec.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

// ----------- Entitate pentru produsul din cosul de cumparaturi ------------------

@Entity
@Table(name = "cart_items")
public class CartItem {

    // ----------- Variabile ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String size;

    @Min(value = 1, message = "Cantitatea trebuie sa fie cel putin 1")
    private int quantity;

    // ----------- Constructori ------------------
    public CartItem() {}

    public CartItem(Cart cart, Product product, String size, int quantity) {
        this.cart = cart;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public CartItem(Long id, Cart cart, Product product, String size, int quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    // ----------- Getteri si Setteri ------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}