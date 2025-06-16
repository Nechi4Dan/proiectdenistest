package com.site.denisalibec.dto;

/**
 * DTO folosit exclusiv pentru inregistrarea unui utilizator nou.
 * Contine campurile necesare completate de utilizator in formularul de inregistrare.
 */
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;

    // Constructor gol (necesar pentru deserializare JSON)
    public RegisterRequest() {}

    // Constructor complet
    public RegisterRequest(String username, String email, String password,
                           String firstName, String lastName, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    // Getteri si Setteri
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
}
