package com.site.denisalibec.controller;

import com.site.denisalibec.dto.CartCreateUpdateDTO;
import com.site.denisalibec.dto.CartDTO;
import com.site.denisalibec.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin
public class CartController {

    // ----------- Variabile ------------------
    private final CartService cartService;

    // ----------- Constructor ------------------
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ----------- Metode ------------------

    // ------- GET: toate cosurile (doar ADMIN) -------
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CartDTO>> getAll() {
        return ResponseEntity.ok(cartService.findAll());
    }

    // ------- GET: cos dupa ID (doar ADMIN) -------
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return cartService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Cosul nu a fost gasit."));
    }

    // ------- POST: creare cos -------
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CartCreateUpdateDTO dto) {
        try {
            CartDTO savedCart = cartService.save(dto);
            return ResponseEntity.ok(savedCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la creare cos: " + e.getMessage());
        }
    }

    // ------- PUT: actualizare cos -------
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody CartCreateUpdateDTO dto) {
        return cartService.update(id, dto)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Cosul nu a putut fi actualizat."));
    }

    // ------- DELETE: stergere cos -------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cartService.delete(id);
            return ResponseEntity.ok("Cosul a fost sters cu succes.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la stergere: " + e.getMessage());
        }
    }

    // ------- GET: cosul utilizatorului logat -------
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyCart(Principal principal) {
        try {
            String username = principal.getName();
            CartDTO myCart = cartService.getCartForUser(username);
            return ResponseEntity.ok(myCart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la preluarea cosului: " + e.getMessage());
        }
    }
}