package com.site.denisalibec.model;

import com.site.denisalibec.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// ----------- Entitate pentru utilizator ------------------

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class User {

    // ----------- Variabile ------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username este obligatoriu")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Emailul este obligatoriu")
    @Email(message = "Email invalid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Parola este obligatorie")
    @Size(min = 5, message = "Parola trebuie sa aiba cel putin 5 caractere")
    private String password;

    @NotBlank(message = "Prenumele este obligatoriu")
    private String firstName;

    @NotBlank(message = "Numele este obligatoriu")
    private String lastName;

    @NotBlank(message = "Telefonul este obligatoriu")
    @Pattern(regexp = "\\d+", message = "Telefonul trebuie sa contina doar cifre")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    // ----------- Constructori ------------------
    public User() {}

    // TODO: Constructor folosit cand convertim din UserDTO in User (ex: creare user)
    public User(String username, String email, String password,
                String firstName, String lastName, String phone, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
    }

    public User(Long id, String username, String email, String password,
                String firstName, String lastName, String phone, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
    }

    // ----------- Getteri si Setteri ------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}