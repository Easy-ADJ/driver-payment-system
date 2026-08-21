package com.example.eazyadj.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "payment_attempt",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_payment_attempt_key",
                        columnNames = "attempt_key"
                ),
                @UniqueConstraint(
                        name = "uk_payment_attempt_tid",
                        columnNames = "tid"
                )
        }
)
public class PaymentAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Long attemptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_id",
            nullable = false
    )
    private Payment payment;

    @Column(
            name = "attempt_key",
            nullable = false,
            unique = true,
            length = 36
    )
    private String attemptKey;

    @Column(
            name = "tid",
            unique = true
    )
    private String tid;

    @Column(
            name = "status",
            nullable = false
    )
    private String status;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "next_redirect_pc_url",
            length = 1000
    )
    private String nextRedirectPcUrl;

    public String getNextRedirectPcUrl() {
        return nextRedirectPcUrl;
    }

    public void setNextRedirectPcUrl(String nextRedirectPcUrl) {
        this.nextRedirectPcUrl = nextRedirectPcUrl;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getAttemptKey() {
        return attemptKey;
    }

    public void setAttemptKey(String attemptKey) {
        this.attemptKey = attemptKey;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }
}