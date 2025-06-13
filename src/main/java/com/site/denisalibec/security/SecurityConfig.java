package com.site.denisalibec.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // --------------------------- Configurare filtru de securitate ---------------------------

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth

                        // ------------------- Public - fara autentificare -------------------
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product-stocks/**").permitAll()

                        // ------------------- Acces doar pentru useri autentificati -------------------
                        .requestMatchers("/api/carts/my").authenticated()
                        .requestMatchers("/api/cart-items/**").authenticated()
                        .requestMatchers("/api/payments/**").authenticated()
                        .requestMatchers("/api/receipts/**").authenticated()

                        // ------------------- Acces doar pentru ADMIN -------------------
                        .requestMatchers("/api/carts/**").hasRole("ADMIN")

                        // ------------------- Alte requesturi -------------------
                        .anyRequest().authenticated()
                )
                .formLogin(customizer -> {}); // activeaza login-ul cu formular

        return http.build();
    }

    // --------------------------- Manager de autentificare ---------------------------

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // --------------------------- Encoder pentru parole ---------------------------

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}