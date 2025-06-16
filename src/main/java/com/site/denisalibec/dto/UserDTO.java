package com.site.denisalibec.dto;

import com.site.denisalibec.enums.Role;

// DTO pentru utilizator - folosit la comunicarea dintre backend si frontend
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String password;


    // Constructor gol - necesar pentru serializare JSON
    public UserDTO() {}

    // Constructor complet - folosit pentru a crea UserDTO din User
    public UserDTO(Long id, String username, String email, String firstName, String lastName, String phone, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
    }

    // Getteri si Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // TODO: daca vei avea functionalitate de editare date user, va fi extins cu metode suplimentare sau validari
    // TODO: parola nu e inclusa intentionat aici → se va gestiona separat printr-un request special de login/register
}

