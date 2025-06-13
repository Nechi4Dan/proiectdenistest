package com.site.denisalibec.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDTO {

    // -------- Variabile --------
    @NotBlank(message = "Parola veche este obligatorie")
    private String oldPassword;

    @NotBlank(message = "Parola noua este obligatorie")
    private String newPassword;

    @NotBlank(message = "Confirmarea parolei noi este obligatorie")
    private String confirmNewPassword;

    // -------- Constructori --------

    public ChangePasswordDTO() {}

    public ChangePasswordDTO(String oldPassword, String newPassword, String confirmNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    // -------- Getters si Setters --------

    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getConfirmNewPassword() { return confirmNewPassword; }
    public void setConfirmNewPassword(String confirmNewPassword) { this.confirmNewPassword = confirmNewPassword; }
}