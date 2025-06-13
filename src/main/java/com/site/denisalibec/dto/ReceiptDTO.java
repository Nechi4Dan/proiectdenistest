package com.site.denisalibec.dto;

import java.time.LocalDateTime;

public class ReceiptDTO {

    // -------- Variabile --------
    private Long id;
    private String filename;
    private LocalDateTime generatedAt;
    private Long paymentId;

    // -------- Constructori --------
    public ReceiptDTO() {}

    public ReceiptDTO(Long id, String filename, LocalDateTime generatedAt, Long paymentId) {
        this.id = id;
        this.filename = filename;
        this.generatedAt = generatedAt;
        this.paymentId = paymentId;
    }

    // -------- Getters si Setters --------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
}