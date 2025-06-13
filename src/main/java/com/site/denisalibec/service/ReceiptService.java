package com.site.denisalibec.service;

import com.site.denisalibec.dto.ReceiptDTO;
import com.site.denisalibec.enums.PaymentStatus;
import com.site.denisalibec.model.Payment;
import com.site.denisalibec.model.Receipt;
import com.site.denisalibec.repository.PaymentRepository;
import com.site.denisalibec.repository.ReceiptRepository;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Service
public class ReceiptService {

    // ----------- Variabile ------------------
    private final ReceiptRepository receiptRepository;
    private final PaymentRepository paymentRepository;

    // ----------- Constructor ----------------
    public ReceiptService(ReceiptRepository receiptRepository, PaymentRepository paymentRepository) {
        this.receiptRepository = receiptRepository;
        this.paymentRepository = paymentRepository;
    }

    // ----------- Metode ------------------

    // Genereaza o factura PDF pentru o plata si o salveaza in baza de date
    public ReceiptDTO generateReceipt(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Plata nu a fost gasita"));

        // Verifica daca plata este finalizata (Paid)
        if (payment.getStatus() != PaymentStatus.PAID) {
            throw new RuntimeException("Plata nu este finalizata, nu se poate emite factura.");
        }

        // Creare PDF (factura)
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);

            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.setLeading(20f);
            content.newLineAtOffset(50, 750);
            content.showText("FACTURA / RECEIPT");
            content.newLine();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.showText("Plata ID: " + payment.getId());
            content.newLine();
            content.showText("Metoda: " + payment.getPaymentType());
            content.newLine();
            content.showText("Status: " + payment.getStatus());
            content.newLine();
            content.showText("Data: " + LocalDateTime.now());
            content.newLine();
            content.showText("Total: " + payment.getTotalAmount() + " RON");
            content.endText();
            content.close();

            document.save(out);

            // Salveaza chitanta in baza de date
            Receipt receipt = new Receipt();
            receipt.setGeneratedAt(LocalDateTime.now());
            receipt.setFilename("receipt_" + paymentId + ".pdf");
            receipt.setPdf(out.toByteArray());
            receipt.setPayment(payment);

            Receipt saved = receiptRepository.save(receipt);
            return toDTO(saved);  // Returnează un DTO cu informațiile chitantei
        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea facturii PDF: " + e.getMessage());
        }
    }

    // Metoda care returnează chitanta din baza de date pe baza unui ID
    public Receipt getReceipt(Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chitanta nu a fost gasita!"));
    }

    // Returneaza un DTO pentru Receipt
    private ReceiptDTO toDTO(Receipt receipt) {
        ReceiptDTO dto = new ReceiptDTO();
        dto.setId(receipt.getId());
        dto.setFilename(receipt.getFilename());
        dto.setGeneratedAt(receipt.getGeneratedAt());
        dto.setPaymentId(receipt.getPayment().getId());
        return dto;
    }
}

