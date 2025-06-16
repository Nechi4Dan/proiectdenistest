package com.site.denisalibec.dto;

import com.site.denisalibec.enums.PaymentType;

public class PaymentRequest {
    private String username;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryAddress;
    private PaymentType paymentType;

    // Pentru plata cu cardul
    private String cardNumber;
    private String cvv;
    private String expiryMonth;
    private String expiryYear;

    // Pentru transfer bancar
    private String iban;

    // Constructori
    public PaymentRequest() {}

    public PaymentRequest(String username, String deliveryName, String deliveryPhone,
                          String deliveryAddress, PaymentType paymentType) {
        this.username = username;
        this.deliveryName = deliveryName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
    }

    // Getteri si Setteri
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

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public String getExpiryMonth() { return expiryMonth; }
    public void setExpiryMonth(String expiryMonth) { this.expiryMonth = expiryMonth; }

    public String getExpiryYear() { return expiryYear; }
    public void setExpiryYear(String expiryYear) { this.expiryYear = expiryYear; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
}