package com.site.denisalibec.service;

import com.site.denisalibec.dto.CartItemCreateUpdateDTO;
import com.site.denisalibec.dto.CartItemDTO;
import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.CartItem;
import com.site.denisalibec.model.Product;
import com.site.denisalibec.repository.CartItemRepository;
import com.site.denisalibec.repository.CartRepository;
import com.site.denisalibec.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    // ------------- Variabile -------------------
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // ------------- Constructor ------------------
    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // ------------- Metode CRUD ------------------

    // Creeaza un cart item
    public CartItemDTO save(CartItemCreateUpdateDTO dto) {
        CartItem item = fromDTO(dto);
        return toDTO(cartItemRepository.save(item));
    }

    // Returneaza toate cart item-urile
    public List<CartItemDTO> findAll() {
        return cartItemRepository.findAll().stream()
                .map(CartItemService::toDTO)
                .collect(Collectors.toList());
    }

    // Returneaza un cart item dupa ID
    public Optional<CartItemDTO> findById(Long id) {
        return cartItemRepository.findById(id)
                .map(CartItemService::toDTO);
    }

    // Returneaza toate cart item-urile dintr-un cart
    public List<CartItemDTO> findByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId).stream()
                .map(CartItemService::toDTO)
                .collect(Collectors.toList());
    }

    // Actualizeaza un cart item existent
    public Optional<CartItemDTO> update(Long id, CartItemCreateUpdateDTO dto) {
        return cartItemRepository.findById(id).map(existing -> {
            Cart cart = cartRepository.findById(dto.getCartId())
                    .orElseThrow(() -> new RuntimeException("Cosul nu a fost gasit cu ID-ul " + dto.getCartId()));
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit cu ID-ul " + dto.getProductId()));

            existing.setCart(cart);
            existing.setProduct(product);
            existing.setSize(dto.getSize());
            existing.setQuantity(dto.getQuantity());

            return toDTO(cartItemRepository.save(existing));
        });
    }

    // Sterge un cart item
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }

    // ------------- Conversii ------------------

    // Conversie din entitate in DTO
    public static CartItemDTO toDTO(CartItem item) {
        return new CartItemDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getSize(),
                item.getQuantity()
        );
    }

    // Conversie din DTO in entitate
    public CartItem fromDTO(CartItemCreateUpdateDTO dto) {
        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cosul nu a fost gasit cu ID-ul " + dto.getCartId()));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost gasit cu ID-ul " + dto.getProductId()));

        return new CartItem(cart, product, dto.getSize(), dto.getQuantity());
    }
}