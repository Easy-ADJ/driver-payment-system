package com.example.eazyadj.Repository;

import com.example.eazyadj.Entity.PaymentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentAttemptRepository
        extends JpaRepository<PaymentAttempt, Long> {

    Optional<PaymentAttempt> findByAttemptKey(
            String attemptKey
    );

    Optional<PaymentAttempt> findByTid(
            String tid
    );
}