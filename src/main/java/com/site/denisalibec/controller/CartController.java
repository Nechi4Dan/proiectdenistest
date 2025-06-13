package com.site.denisalibec.controller;

import com.site.denisalibec.dto.CartCreateUpdateDTO;
import com.site.denisalibec.dto.CartDTO;
import com.site.denisalibec.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/carts")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CartDTO> getById(@PathVariable Long id) {
        return cartService.findById(id)
                .map(ResponseEntity::ok)  // Returnează DTO-ul pentru succes
                .orElseGet(() -> ResponseEntity.status(404).body(null)); // Returnează 404 în caz de eroare
    }

    @PostMapping
    public ResponseEntity<CartDTO> create(@Valid @RequestBody CartCreateUpdateDTO dto) {
        try {
            CartDTO savedCart = cartService.save(dto);
            return ResponseEntity.ok(savedCart);  // Returnează DTO-ul creat
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Poți să gestionezi eroarea mai specific
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDTO> update(@PathVariable Long id, @Valid @RequestBody CartCreateUpdateDTO dto) {
        return cartService.update(id, dto)
                .map(ResponseEntity::ok)  // Returnează DTO-ul pentru succes
                .orElseGet(() -> ResponseEntity.status(404).body(null)); // Returnează 404 în caz de eroare
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            cartService.delete(id);
            return ResponseEntity.ok().build(); // Returnează un răspuns de succes fără corp
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Poți să gestionezi eroarea mai specific
        }
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDTO> getMyCart(Principal principal) {
        try {
            String username = principal.getName();
            CartDTO myCart = cartService.getCartForUser(username);
            return ResponseEntity.ok(myCart);  // Returnează DTO-ul cosului utilizatorului
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null); // Returnează 404 în caz de eroare
        }
    }
}


