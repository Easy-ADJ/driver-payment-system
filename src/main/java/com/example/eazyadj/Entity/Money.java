package com.example.eazyadj.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "money")
public class Money {

    @Id
    @Column(name = "payment_id")
    private Long paymentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "tax_free")
    private BigDecimal taxFree;

    @Column(name = "vat")
    private BigDecimal vat;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "driver_id")
    private Long driverId;

    public Long getPaymentId() {
        return paymentId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTaxFree() {
        return taxFree;
    }

    public void setTaxFree(BigDecimal taxFree) {
        this.taxFree = taxFree;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
}