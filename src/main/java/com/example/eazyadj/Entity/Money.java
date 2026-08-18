package com.example.eazyadj.Entity;

import io.netty.util.internal.IntegerHolder;
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
    private Integer amount;

    @Column(name = "tax_free")
    private Integer taxFree;

    @Column(name = "vat")
    private Integer vat;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTaxFree() {
        return taxFree;
    }

    public void setTaxFree(Integer taxFree) {
        this.taxFree = taxFree;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
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