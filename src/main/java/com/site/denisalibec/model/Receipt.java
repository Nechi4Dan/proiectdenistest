package com.site.denisalibec.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// ----------- Entitate pentru chitanta ------------------

@Entity
@Table(name = "receipts")
public class Receipt {

    // ----------- Variabile ------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private LocalDateTime generatedAt;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] pdf;

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false, unique = true)
    private Payment payment;

    // ----------- Constructori ------------------

    public Receipt() {}

    public Receipt(String filename, LocalDateTime generatedAt, byte[] pdf, Payment payment) {
        this.filename = filename;
        this.generatedAt = generatedAt;
        this.pdf = pdf;
        this.payment = payment;
    }

    public Receipt(Long id, String filename, LocalDateTime generatedAt, byte[] pdf, Payment payment) {
        this.id = id;
        this.filename = filename;
        this.generatedAt = generatedAt;
        this.pdf = pdf;
        this.payment = payment;
    }

    // ----------- Getteri si Setteri ------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public byte[] getPdf() { return pdf; }
    public void setPdf(byte[] pdf) { this.pdf = pdf; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
}
