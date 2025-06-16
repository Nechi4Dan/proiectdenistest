package com.site.denisalibec.service;

import com.site.denisalibec.dto.AddToCartRequest;
import com.site.denisalibec.dto.CartItemDTO;
import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.CartItem;
import com.site.denisalibec.model.Product;
import com.site.denisalibec.model.User;
import com.site.denisalibec.repository.CartItemRepository;
import com.site.denisalibec.repository.CartRepository;
import com.site.denisalibec.repository.ProductRepository;
import com.site.denisalibec.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // 1. Returneaza cosul unui user
    public Optional<Cart> getCartByUsername(String username) {
        return userRepository.findByUsername(username)
                .flatMap(cartRepository::findByUser);
    }

    // 2. Creeaza cos daca nu exista
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart(user);
            return cartRepository.save(cart);
        });
    }

    // 3. Adauga produs in cos
    public CartItemDTO addToCart(String username, AddToCartRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User inexistent"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Produs inexistent"));

        Cart cart = getOrCreateCart(user);

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(request.getProductId()) &&
                    item.getSize().equalsIgnoreCase(request.getSize())) {
                item.setQuantity(item.getQuantity() + request.getQuantity());
                return convertToDTO(cartItemRepository.save(item));
            }
        }

        CartItem newItem = new CartItem(cart, product, request.getSize(), request.getQuantity());
        return convertToDTO(cartItemRepository.save(newItem));
    }

    // 4. Sterge un item din cos
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // 5. Goleste cosul
    public void clearCart(String username) {
        getCartByUsername(username).ifPresent(cart -> {
            List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
            cartItemRepository.deleteAll(items);
        });
    }

    // 6. Returneaza toate itemele din cos
    public List<CartItemDTO> getItemsForUser(String username) {
        return getCartByUsername(username)
                .map(cart -> cart.getItems().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    // 7. Conversie CartItem -> DTO (UPDATED cu imaginea)
    private CartItemDTO convertToDTO(CartItem item) {
        return new CartItemDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getSize(),
                item.getQuantity(),
                item.getProduct().getPrice(),
                item.getProduct().getImage() // ADAUGAT imaginea produsului
        );
    }
}
