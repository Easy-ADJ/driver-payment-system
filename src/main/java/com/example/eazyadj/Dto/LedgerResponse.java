package com.example.eazyadj.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LedgerResponse {

    private Long paymentId;
    private Integer amount;
    private LocalDateTime approvedAt;

    public LedgerResponse(
            Long paymentId,
            Integer amount,
            LocalDateTime approvedAt
    ) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.approvedAt = approvedAt;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Integer getAmount() {
        return amount;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }
}