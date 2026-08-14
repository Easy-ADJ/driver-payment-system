package com.example.eazyadj.Repository;

import com.example.eazyadj.Entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository
        extends JpaRepository<Money, Long> {
}