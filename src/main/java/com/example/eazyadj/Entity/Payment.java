package com.example.eazyadj.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "payment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_payment_idempotency_key",
                        columnNames = "idempotency_key"
                ),
                @UniqueConstraint(
                        name = "uk_payment_partner_order_id",
                        columnNames = "partner_order_id"
                )
        }
)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(
            name = "idempotency_key",
            nullable = false,
            unique = true,
            length = 36
    )
    private String idempotencyKey;

    @Column(name = "payment_method_type")
    private String paymentMethodType;


    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(
            name = "partner_order_id",
            nullable = false,
            unique = true
    )
    private String partnerOrderId;

    @Column(
            name = "partner_user_id",
            nullable = false
    )
    private String partnerUserId;

    @Column(name = "driver_id")
    private String driverId;

    @Column(
            name = "status",
            nullable = false
    )
    private String status;

    @OneToOne(
            mappedBy = "payment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Money money;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(
            String idempotencyKey
    ) {
        this.idempotencyKey =
                idempotencyKey;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(
            String paymentMethodType
    ) {
        this.paymentMethodType =
                paymentMethodType;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(
            LocalDateTime approvedAt
    ) {
        this.approvedAt =
                approvedAt;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(
            String partnerOrderId
    ) {
        this.partnerOrderId =
                partnerOrderId;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(
            String partnerUserId
    ) {
        this.partnerUserId =
                partnerUserId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(
            String driverId
    ) {
        this.driverId =
                driverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status
    ) {
        this.status =
                status;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(
            Money money
    ) {

        this.money = money;

        if (money != null) {
            money.setPayment(this);
        }
    }
}