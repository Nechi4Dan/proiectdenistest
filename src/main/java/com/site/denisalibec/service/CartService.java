package com.site.denisalibec.service;

import com.site.denisalibec.dto.CartCreateUpdateDTO;
import com.site.denisalibec.dto.CartDTO;
import com.site.denisalibec.dto.CartItemDTO;
import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.CartItem;
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
        Cart cart = fromDTO(dto); // mapare din DTO in Cart
        return toDTO(cartRepository.save(cart)); // salvare si returnare DTO
    }

    // Validare cos gol sau produse invalide
    public void validateCartBeforeCheckout(Cart cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cosul de cumparaturi este gol. Te rugam sa adaugi produse in cos.");
        }

        for (CartItem item : cart.getItems()) {
            if (item.getQuantity() <= 0) {
                throw new RuntimeException("Cantitatea produsului " + item.getProduct().getName() + " trebuie sa fie mai mare de 0.");
            }

            if (item.getQuantity() > item.getProduct().getStock()) {
                throw new RuntimeException("Cantitatea produsului " + item.getProduct().getName() + " depaseste stocul disponibil.");
            }
        }
    }

    // Returneaza un DTO pentru Cart
    public CartDTO toDTO(Cart cart) {
        List<CartItemDTO> items = cartItemRepository.findByCartId(cart.getId()).stream()
                .map(CartItemService::toDTO) // presupunând că ai un CartItemService care face maparea corectă
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
        return new Cart(user); // crează un cart nou asociat utilizatorului
    }

    // Returneaza cart-ul pentru un username
    public CartDTO getCartForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit: " + username));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cosul nu a fost gasit pentru utilizatorul " + username));

        return toDTO(cart); // returnează DTO-ul corespunzător
    }

    // Returneaza cart-ul existent sau creeaza unul nou pentru utilizator
    public CartDTO getOrCreateCartForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit: " + username));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user))); // dacă nu există, creează-l

        return toDTO(cart); // returnează DTO-ul corespunzător
    }

    // Metoda pentru a găsi toate cart-urile
    public List<CartDTO> findAll() {
        return cartRepository.findAll().stream()
                .map(this::toDTO) // mapare din entitate Cart în DTO
                .collect(Collectors.toList());
    }

    // Metoda pentru a găsi un cart după ID
    public Optional<CartDTO> findById(Long id) {
        return cartRepository.findById(id)
                .map(this::toDTO); // mapare din entitate Cart în DTO
    }

    // Metoda pentru a actualiza un cart
    public Optional<CartDTO> update(Long id, CartCreateUpdateDTO dto) {
        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            // Logica de actualizare a câmpurilor
            cart.setUser(userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit cu ID-ul " + dto.getUserId())));
            return Optional.of(toDTO(cartRepository.save(cart))); // returnează cart-ul actualizat
        }
        return Optional.empty(); // în caz de cart inexistent
    }

    // Metoda pentru a șterge un cart
    public void delete(Long id) {
        cartRepository.deleteById(id); // șterge cart-ul după ID
    }
}
