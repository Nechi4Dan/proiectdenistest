package com.site.denisalibec.model;

import com.site.denisalibec.enums.PaymentStatus;
import com.site.denisalibec.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

// ----------- Entitate pentru plata ------------------

@Entity
@Table(name = "payments")
public class Payment {

    // ----------- Variabile ------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Numele pentru livrare este obligatoriu")
    private String deliveryName;

    @NotBlank(message = "Telefonul pentru livrare este obligatoriu")
    @Pattern(regexp = "\\d+", message = "Telefonul trebuie sa contina doar cifre")
    private String deliveryPhone;

    @NotBlank(message = "Adresa de livrare este obligatorie")
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Positive(message = "Suma totala trebuie sa fie pozitiva")
    private double totalAmount;

    private LocalDateTime createdAt = LocalDateTime.now();

    // ----------- Constructori ------------------

    public Payment() {}

    public Payment(Cart cart, User user, String deliveryName, String deliveryPhone, String deliveryAddress,
                   PaymentType paymentType, PaymentStatus status, double totalAmount, LocalDateTime createdAt) {
        this.cart = cart;
        this.user = user;
        this.deliveryName = deliveryName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public Payment(Long id, Cart cart, User user, String deliveryName, String deliveryPhone, String deliveryAddress,
                   PaymentType paymentType, PaymentStatus status, double totalAmount, LocalDateTime createdAt) {
        this.id = id;
        this.cart = cart;
        this.user = user;
        this.deliveryName = deliveryName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryAddress = deliveryAddress;
        this.paymentType = paymentType;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    // ----------- Getteri si Setteri ------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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