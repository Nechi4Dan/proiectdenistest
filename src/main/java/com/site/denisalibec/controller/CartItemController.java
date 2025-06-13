package com.site.denisalibec.controller;

import com.site.denisalibec.dto.CartItemCreateUpdateDTO;
import com.site.denisalibec.dto.CartItemDTO;
import com.site.denisalibec.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // ------- GET: toate itemele -------
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getAll() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    // ------- GET: item dupa ID -------
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return cartItemService.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Itemul nu a fost gasit."));
    }

    // ------- GET: toate itemele pentru un cos -------
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemDTO>> getByCartId(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.findByCartId(cartId));
    }

    // ------- POST: creare item -------
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CartItemCreateUpdateDTO dto) {
        try {
            CartItemDTO savedItem = cartItemService.save(dto);
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la creare item: " + e.getMessage());
        }
    }

    // ------- PUT: actualizare item -------
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody CartItemCreateUpdateDTO dto) {
        return cartItemService.update(id, dto)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Itemul nu a putut fi actualizat."));
    }

    // ------- DELETE: stergere item -------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cartItemService.delete(id);
            return ResponseEntity.ok("Itemul a fost sters cu succes.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la stergere: " + e.getMessage());
        }
    }
}