package com.example.eazyadj.Repository;

import com.example.eazyadj.Entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyRepository
        extends JpaRepository<Money, Long> {

    List<Money> findByDriverId(Long driverId);
}