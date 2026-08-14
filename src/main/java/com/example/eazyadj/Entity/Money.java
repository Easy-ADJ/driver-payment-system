package com.example.eazyadj.Entity;

import com.example.eazyadj.Entity.Payment;
import jakarta.persistence.*;

import java.math.BigDecimal;

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

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "tax_free")
    private BigDecimal taxFree;

    @Column(name = "vat")
    private BigDecimal vat;

    public Long getPaymentId() {
        return paymentId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
}