package com.site.denisalibec.service;

import com.site.denisalibec.dto.CartCreateUpdateDTO;
import com.site.denisalibec.dto.CartDTO;
import com.site.denisalibec.dto.CartItemDTO;
import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.User;
import com.site.denisalibec.repository.CartItemRepository;
import com.site.denisalibec.repository.CartRepository;
import com.site.denisalibec.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    // ------------- Variabile -------------------
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    // ------------- Constructor ------------------
    public CartService(CartRepository cartRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // ------------- Metode -----------------------

    // Creeaza un cart nou pentru un user
    public CartDTO save(CartCreateUpdateDTO dto) {
        Cart cart = fromDTO(dto);
        return toDTO(cartRepository.save(cart));
    }

    // Returneaza toate cart-urile din baza de date
    public List<CartDTO> findAll() {
        return cartRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Returneaza un cart dupa ID
    public Optional<CartDTO> findById(Long id) {
        return cartRepository.findById(id)
                .map(this::toDTO);
    }

    // Actualizeaza un cart existent
    public Optional<CartDTO> update(Long id, CartCreateUpdateDTO dto) {
        return cartRepository.findById(id).map(existing -> {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit cu ID-ul " + dto.getUserId()));
            existing.setUser(user);
            return toDTO(cartRepository.save(existing));
        });
    }

    // Sterge un cart dupa ID
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

    // Converteste entitatea Cart in DTO
    public CartDTO toDTO(Cart cart) {
        List<CartItemDTO> items = cartItemRepository.findByCartId(cart.getId()).stream()
                .map(CartItemService::toDTO)
                .collect(Collectors.toList());

        return new CartDTO(
                cart.getId(),
                cart.getUser().getUsername(),
                items
        );
    }

    // Creeaza un obiect Cart din DTO
    public Cart fromDTO(CartCreateUpdateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit cu ID-ul " + dto.getUserId()));
        return new Cart(user);
    }

    // Returneaza cart-ul pentru un username
    public CartDTO getCartForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit: " + username));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cosul nu a fost gasit pentru utilizatorul " + username));

        return toDTO(cart);
    }

    // Returneaza cart-ul existent sau creeaza unul nou pentru utilizator
    public CartDTO getOrCreateCartForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit: " + username));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user)));

        return toDTO(cart);
    }
}