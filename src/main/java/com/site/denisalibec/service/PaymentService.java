package com.site.denisalibec.service;

import com.site.denisalibec.dto.PaymentCreateUpdateDTO;
import com.site.denisalibec.dto.PaymentDTO;
import com.site.denisalibec.enums.PaymentStatus;
import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.Payment;
import com.site.denisalibec.model.User;
import com.site.denisalibec.repository.CartRepository;
import com.site.denisalibec.repository.PaymentRepository;
import com.site.denisalibec.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    // ----------- Variabile ------------------
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    // ----------- Constructor ----------------
    public PaymentService(PaymentRepository paymentRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    // ----------- Metode ------------------

    // Returneaza toate platile pentru utilizatorul logat
    public List<PaymentDTO> getAllForUser(Principal principal) {
        User user = getUserFromPrincipal(principal);
        return paymentRepository.findByUser(user).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Returneaza o plata dupa ID pentru utilizatorul logat
    public PaymentDTO getByIdForUser(Long id, Principal principal) {
        User user = getUserFromPrincipal(principal);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plata nu a fost gasita"));

        if (!payment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acces interzis");
        }

        return toDTO(payment);
    }

    // Creeaza o noua plata pentru utilizatorul logat
    @Transactional
    public PaymentDTO create(PaymentCreateUpdateDTO dto, Principal principal) {
        User user = getUserFromPrincipal(principal);
        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cosul nu a fost gasit"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Nu ai acces la acest cos");
        }

        Payment payment = new Payment();
        payment.setCart(cart);
        payment.setUser(user);
        payment.setDeliveryName(dto.getDeliveryName());
        payment.setDeliveryPhone(dto.getDeliveryPhone());
        payment.setDeliveryAddress(dto.getDeliveryAddress());
        payment.setPaymentType(dto.getPaymentType());
        payment.setTotalAmount(dto.getTotalAmount());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        return toDTO(paymentRepository.save(payment));
    }

    // Sterge o plata daca apartine utilizatorului logat
    public void delete(Long id, Principal principal) {
        User user = getUserFromPrincipal(principal);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plata nu a fost gasita"));

        if (!payment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acces interzis");
        }

        paymentRepository.delete(payment);
    }

    // Converteste entitatea Payment in DTO
    public PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setCartId(payment.getCart().getId());
        dto.setDeliveryName(payment.getDeliveryName());
        dto.setDeliveryPhone(payment.getDeliveryPhone());
        dto.setDeliveryAddress(payment.getDeliveryAddress());
        dto.setPaymentType(payment.getPaymentType());
        dto.setStatus(payment.getStatus());
        dto.setTotalAmount(payment.getTotalAmount());
        dto.setCreatedAt(payment.getCreatedAt());
        return dto;
    }

    // Returneaza utilizatorul logat pe baza obiectului Principal
    private User getUserFromPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit"));
    }
}