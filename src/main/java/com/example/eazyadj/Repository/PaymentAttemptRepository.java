package com.example.eazyadj.Repository;

import com.example.eazyadj.Entity.PaymentAttempt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PaymentAttemptRepository
        extends JpaRepository<PaymentAttempt, Long> {

    Optional<PaymentAttempt> findByAttemptKey(
            String attemptKey
    );

    Optional<PaymentAttempt> findByTid(
            String tid
    );

    @Transactional
    @Modifying(
            clearAutomatically = true,
            flushAutomatically = true
    )
    @Query("""
        UPDATE PaymentAttempt pa
           SET pa.status = 'INVALIDATED'
         WHERE pa.payment.paymentId = :paymentId
           AND pa.status IN (
               'READY_REQUESTED',
               'READY',
               'APPROVE_FAILED'
           )
        """)
    int invalidateAllAttempts(
            @Param("paymentId")
            Long paymentId
    );

}