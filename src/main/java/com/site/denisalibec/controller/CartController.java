package com.site.denisalibec.controller;

import com.site.denisalibec.dto.AddToCartRequest;
import com.site.denisalibec.dto.CartItemDTO;
import com.site.denisalibec.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 1. Returneaza toate produsele din cosul unui user
    @GetMapping("/{username}")
    public ResponseEntity<List<CartItemDTO>> getCartForUser(@PathVariable String username) {
        List<CartItemDTO> items = cartService.getItemsForUser(username);
        return ResponseEntity.ok(items);
    }

    // 2. Adauga un produs in cos
    @PostMapping("/{username}/add")
    public ResponseEntity<?> addToCart(
            @PathVariable String username,
            @RequestBody AddToCartRequest request
    ) {
        try {
            CartItemDTO item = cartService.addToCart(username, request);
            return ResponseEntity.ok(item);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Eroare: " + e.getMessage());
        }
    }

    // 3. Sterge un produs din cos
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.ok("Produs sters din cos.");
    }

    // 4. Goleste complet cosul
    @DeleteMapping("/{username}/clear")
    public ResponseEntity<String> clearCart(@PathVariable String username) {
        cartService.clearCart(username);
        return ResponseEntity.ok("Cosul a fost golit.");
    }
}

