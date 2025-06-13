package com.site.denisalibec.config;

import com.site.denisalibec.enums.Role;
import com.site.denisalibec.model.User;
import com.site.denisalibec.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    // --------------------------- Metode ----------------------------------

    // Creeaza un utilizator admin default la prima rulare daca nu exista deja
    @Bean
    public CommandLineRunner createDefaultAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(encoder.encode("admin123")); // parola e criptata
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setPhone("0000000000");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin default creat: admin / admin123");
            }
        };
    }
}