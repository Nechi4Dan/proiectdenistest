package com.site.denisalibec.controller;

import com.site.denisalibec.dto.ReceiptDTO;
import com.site.denisalibec.model.Receipt;
import com.site.denisalibec.service.ReceiptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // ------- POST: genereaza chitanta pentru o plata -------
    @PostMapping("/generate/{paymentId}")
    public ResponseEntity<?> generate(@PathVariable Long paymentId) {
        try {
            ReceiptDTO dto = receiptService.generateReceipt(paymentId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la generarea chitantei: " + e.getMessage());
        }
    }

    // ------- GET: obtine PDF-ul chitantei dupa ID -------
    @GetMapping("/{id}/pdf")
    public ResponseEntity<?> getPdf(@PathVariable Long id) {
        try {
            Receipt receipt = receiptService.getReceipt(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + receipt.getFilename() + "\"")
                    .body(receipt.getPdf());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la descarcarea PDF-ului: " + e.getMessage());
        }
    }
}
