package com.site.denisalibec.service;

import com.site.denisalibec.dto.PaymentRequest;
import com.site.denisalibec.dto.PaymentDTO;
import com.site.denisalibec.enums.PaymentStatus;
import com.site.denisalibec.enums.PaymentType;
import com.site.denisalibec.model.Cart;
import com.site.denisalibec.model.CartItem;
import com.site.denisalibec.model.Payment;
import com.site.denisalibec.model.User;
import com.site.denisalibec.repository.PaymentRepository;
import com.site.denisalibec.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    public PaymentService(PaymentRepository paymentRepository,
                          UserRepository userRepository,
                          CartService cartService) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    public PaymentDTO processPayment(PaymentRequest request) {
        // 1. Validare utilizator
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit"));

        // 2. Obtinere cos
        Cart cart = cartService.getCartByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Cosul nu a fost gasit"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cosul este gol");
        }

        // 3. Calculare total
        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 4. Validare date plata
        validatePaymentData(request);

        // 5. Procesare plata (simulare)
        String transactionId = generateTransactionId();
        PaymentStatus status = processPaymentByType(request);

        // 6. Creare plata
        Payment payment = new Payment();
        payment.setCart(cart);
        payment.setUser(user);
        payment.setDeliveryName(request.getDeliveryName());
        payment.setDeliveryPhone(request.getDeliveryPhone());
        payment.setDeliveryAddress(request.getDeliveryAddress());
        payment.setPaymentType(request.getPaymentType());
        payment.setStatus(status);
        payment.setTotalAmount(totalAmount);
        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // 7. Golire cos dupa plata reusita
        if (status == PaymentStatus.PAID) {
            cartService.clearCart(request.getUsername());
        }

        return convertToDTO(savedPayment, transactionId);
    }

    private void validatePaymentData(PaymentRequest request) {
        // Validare date livrare
        if (request.getDeliveryName() == null || request.getDeliveryName().trim().isEmpty()) {
            throw new RuntimeException("Numele pentru livrare este obligatoriu");
        }

        if (request.getDeliveryPhone() == null || !request.getDeliveryPhone().matches("\\d+")) {
            throw new RuntimeException("Telefonul trebuie sa contina doar cifre");
        }

        if (request.getDeliveryAddress() == null || request.getDeliveryAddress().trim().isEmpty()) {
            throw new RuntimeException("Adresa de livrare este obligatorie");
        }

        // Validare specifice tipului de plata
        if (request.getPaymentType() == PaymentType.CARD) {
            validateCardData(request);
        } else if (request.getPaymentType() == PaymentType.BANK_TRANSFER) {
            validateBankTransferData(request);
        }
    }

    private void validateCardData(PaymentRequest request) {
        // Validare numar card (16 cifre)
        if (request.getCardNumber() == null || !request.getCardNumber().matches("\\d{16}")) {
            throw new RuntimeException("Numarul cardului trebuie sa contina exact 16 cifre");
        }

        // Validare CVV (3 cifre)
        if (request.getCvv() == null || !request.getCvv().matches("\\d{3}")) {
            throw new RuntimeException("CVV-ul trebuie sa contina exact 3 cifre");
        }

        // Validare data expirare
        validateExpiryDate(request.getExpiryMonth(), request.getExpiryYear());
    }

    private void validateExpiryDate(String month, String year) {
        try {
            int expMonth = Integer.parseInt(month);
            int expYear = Integer.parseInt(year);

            if (expMonth < 1 || expMonth > 12) {
                throw new RuntimeException("Luna trebuie sa fie intre 1 si 12");
            }

            YearMonth currentYearMonth = YearMonth.now();
            YearMonth expiryYearMonth = YearMonth.of(expYear, expMonth);

            if (expiryYearMonth.isBefore(currentYearMonth)) {
                throw new RuntimeException("Data de expirare a cardului nu poate fi in trecut");
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException("Data de expirare invalida");
        }
    }

    private void validateBankTransferData(PaymentRequest request) {
        if (request.getIban() == null || request.getIban().length() != 24) {
            throw new RuntimeException("IBAN-ul trebuie sa contina exact 24 de caractere");
        }
    }

    private PaymentStatus processPaymentByType(PaymentRequest request) {
        // Simulare procesare plata
        switch (request.getPaymentType()) {
            case CARD:
                // Simulare procesare card - 95% succes
                return Math.random() > 0.05 ? PaymentStatus.PAID : PaymentStatus.FAILED;

            case BANK_TRANSFER:
                // Transfer bancar ramane pending
                return PaymentStatus.PENDING;

            case CASH_ON_DELIVERY:
                // Ramburs e confirmat direct
                return PaymentStatus.PAID;

            default:
                throw new RuntimeException("Tip de plata nesuportat");
        }
    }

    private String generateTransactionId() {
        return "TXN_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<PaymentDTO> getPaymentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost gasit"));

        return paymentRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(payment -> convertToDTO(payment, null))
                .collect(Collectors.toList());
    }

    public Optional<PaymentDTO> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(payment -> convertToDTO(payment, null));
    }

    private PaymentDTO convertToDTO(Payment payment, String transactionId) {
        return new PaymentDTO(
                payment.getId(),
                payment.getUser().getUsername(),
                payment.getDeliveryName(),
                payment.getDeliveryPhone(),
                payment.getDeliveryAddress(),
                payment.getPaymentType(),
                payment.getStatus(),
                payment.getTotalAmount(),
                payment.getCreatedAt(),
                transactionId
        );
    }
}