package com.site.denisalibec.service;

import com.site.denisalibec.dto.ChangePasswordDTO;
import com.site.denisalibec.enums.Role;
import com.site.denisalibec.model.User;
import com.site.denisalibec.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // ----------- Variabile ------------------
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ------------- Constructor-------------------
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.userRepository = repo;
        this.passwordEncoder = encoder;
    }

    // ----------- Metode ------------------

    // Inregistrare utilizator nou in baza de date
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email deja folosit!");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username deja folosit!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole() == null ? Role.CLIENT : user.getRole());
        return userRepository.save(user);
    }

    // Cautare utilizator dupa username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu exista"));
    }

    // Schimbare parola pentru utilizator logat
    public void changePassword(ChangePasswordDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit."));

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Parola veche este incorecta.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
            throw new RuntimeException("Parolele noi nu se potrivesc.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }
}