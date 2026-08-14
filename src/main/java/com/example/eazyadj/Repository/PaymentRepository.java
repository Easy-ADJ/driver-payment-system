package com.example.eazyadj.Repository;

import com.example.eazyadj.Entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPartnerOrderId(
            String partnerOrderId
    );

    Optional<Payment> findByIdempotencyKey(
            String idempotencyKey
    );

    @Transactional
    @Modifying(
            clearAutomatically = true,
            flushAutomatically = true
    )
    @Query("""
            UPDATE Payment p
               SET p.status = 'APPROVING'
             WHERE p.paymentId = :paymentId
               AND p.status = 'READY'
            """)
    int changeReadyToApproving(
            @Param("paymentId")
            Long paymentId
    );

    @Transactional
    @Modifying(
            clearAutomatically = true,
            flushAutomatically = true
    )
    @Query("""
            UPDATE Payment p
               SET p.status = :status
             WHERE p.paymentId = :paymentId
            """)
    int updateStatus(
            @Param("paymentId")
            Long paymentId,

            @Param("status")
            String status
    );
}