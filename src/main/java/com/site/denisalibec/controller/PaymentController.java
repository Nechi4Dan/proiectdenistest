package com.site.denisalibec.controller;

import com.site.denisalibec.dto.PaymentRequest;
import com.site.denisalibec.dto.PaymentDTO;
import com.site.denisalibec.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Procesare plata
    @PostMapping("/process")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request) {
        try {
            PaymentDTO payment = paymentService.processPayment(request);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Eroare la procesarea platii: " + e.getMessage());
        }
    }

    // Obtinere plati pentru un user
    @GetMapping("/user/{username}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsForUser(@PathVariable String username) {
        List<PaymentDTO> payments = paymentService.getPaymentsForUser(username);
        return ResponseEntity.ok(payments);
    }

    // Obtinere detalii plata
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentDetails(@PathVariable Long paymentId) {
        return paymentService.getPaymentById(paymentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}