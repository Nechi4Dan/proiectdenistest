package com.site.denisalibec.dto;

import com.site.denisalibec.enums.PaymentStatus;
import com.site.denisalibec.enums.PaymentType;

import java.time.LocalDateTime;

public class PaymentDTO {
    private Long id;
    private String username;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryAddress;
    private PaymentType paymentType;
    private PaymentStatus status;
    private double totalAmount;
    private LocalDateTime createdAt;
    private String transactionId;

    // Constructori
    public PaymentDTO() {}

    public PaymentDTO(Long id, String username, String deliveryName, String deliveryPhone,
                      String deliveryAddress, PaymentType paymentType, PaymentStatus status,
                      double totalAmount, LocalDateTime createdAt, String transactionId) {
        this.id = id;
        this.username = username;
        this.deliveryName = deliveryName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.transactionId = transactionId;
    }

    // Getteri si Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDeliveryName() { return deliveryName; }
    public void setDeliveryName(String deliveryName) { this.deliveryName = deliveryName; }

    public String getDeliveryPhone() { return deliveryPhone; }
    public void setDeliveryPhone(String deliveryPhone) { this.deliveryPhone = deliveryPhone; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
}