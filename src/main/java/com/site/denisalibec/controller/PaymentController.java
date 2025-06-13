package com.site.denisalibec.controller;

import com.site.denisalibec.dto.PaymentCreateUpdateDTO;
import com.site.denisalibec.dto.PaymentDTO;
import com.site.denisalibec.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ------- GET: toate platile utilizatorului logat -------
    @GetMapping
    public ResponseEntity<?> getAll(Principal principal) {
        try {
            List<PaymentDTO> payments = paymentService.getAllForUser(principal);
            return ResponseEntity.ok(payments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la preluarea platilor: " + e.getMessage());
        }
    }

    // ------- GET: o plata dupa ID (doar daca apartine userului) -------
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, Principal principal) {
        try {
            PaymentDTO payment = paymentService.getByIdForUser(id, principal);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la preluarea platii: " + e.getMessage());
        }
    }

    // ------- POST: creare plata -------
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PaymentCreateUpdateDTO dto, Principal principal) {
        try {
            PaymentDTO created = paymentService.create(dto, principal);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la creare plata: " + e.getMessage());
        }
    }

    // ------- DELETE: stergere plata (doar daca apartine userului) -------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        try {
            paymentService.delete(id, principal);
            return ResponseEntity.ok("Plata a fost stearsa cu succes.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la stergere: " + e.getMessage());
        }
    }
}