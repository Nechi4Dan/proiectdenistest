package com.site.denisalibec.dto;

import com.site.denisalibec.enums.PaymentStatus;
import com.site.denisalibec.enums.PaymentType;

import java.time.LocalDateTime;

public class PaymentDTO {

    // -------- Variabile --------
    private Long id;
    private Long cartId;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryAddress;
    private PaymentType paymentType;
    private PaymentStatus status;
    private double totalAmount;
    private LocalDateTime createdAt;

    // -------- Constructori --------

    public PaymentDTO() {}

    public PaymentDTO(Long id, Long cartId, String deliveryName, String deliveryPhone,
                      String deliveryAddress, PaymentType paymentType, PaymentStatus status,
                      double totalAmount, LocalDateTime createdAt) {
        this.id = id;
        this.cartId = cartId;
        this.deliveryName = deliveryName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    // -------- Getters si Setters --------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

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
}