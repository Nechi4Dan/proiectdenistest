package com.site.denisalibec.model;

import jakarta.persistence.*;

// ----------- Entitate pentru cosul de cumparaturi ------------------

@Entity
@Table(name = "carts")
public class Cart {

    // ----------- Variabile ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // ----------- Constructori ------------------
    public Cart() {}

    public Cart(User user) {
        this.user = user;
    }

    // ----------- Getteri si Setteri ------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}