package com.site.denisalibec.dto;

import java.util.List;

public class CartDTO {

    // -------- Variabile --------
    private Long id;
    private String username;
    private List<CartItemDTO> items;

    // -------- Constructori --------

    public CartDTO() {}

    public CartDTO(Long id, String username, List<CartItemDTO> items) {
        this.id = id;
        this.username = username;
        this.items = items;
    }

    // -------- Getters si Setters --------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<CartItemDTO> getItems() { return items; }
    public void setItems(List<CartItemDTO> items) { this.items = items; }
}