package com.site.denisalibec.controller;

import com.site.denisalibec.dto.ChangePasswordDTO;
import com.site.denisalibec.model.User;
import com.site.denisalibec.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    // ----------- Variabile ------------------
    private final UserService userService;

    // ----------- Constructor ------------------
    public AuthController(UserService service) {
        this.userService = service;
    }

    // ----------- Metode ------------------

    // ------- POST: inregistrare utilizator -------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.register(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la inregistrare: " + e.getMessage());
        }
    }

    // ------- PUT: schimbare parola (utilizator logat) -------
    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDTO dto) {
        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            return ResponseEntity.badRequest().body("Parolele noi nu se potrivesc");
        }

        try {
            userService.changePassword(dto);
            return ResponseEntity.ok("Parola a fost schimbata cu succes");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la schimbarea parolei: " + e.getMessage());
        }
    }
}