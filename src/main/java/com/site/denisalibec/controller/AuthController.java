package com.site.denisalibec.controller;

import com.site.denisalibec.dto.LoginRequest;
import com.site.denisalibec.dto.RegisterRequest;
import com.site.denisalibec.dto.UserDTO;
import com.site.denisalibec.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ========== REGISTER ==========
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username-ul este deja folosit.");
        }

        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Emailul este deja folosit.");
        }

        UserDTO createdUser = userService.registerUser(request);
        return ResponseEntity.ok(createdUser);
    }

    // ========== LOGIN ==========
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Optional<UserDTO> user = userService.loginUser(request);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Username sau parola invalida.");
        }

        return ResponseEntity.ok(user.get());
    }

}
