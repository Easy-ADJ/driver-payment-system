package com.example.eazyadj.Service;

import com.example.eazyadj.Dto.LedgerResponse;
import com.example.eazyadj.Entity.Money;
import com.example.eazyadj.Repository.MoneyRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyService {

    private final MoneyRepository moneyRepository;

    public MoneyService(
            MoneyRepository moneyRepository
    ) {
        this.moneyRepository = moneyRepository;
    }

    public List<LedgerResponse> getLedger(
            Long driverId
    ) {

        List<Money> moneyList =
                moneyRepository.findByDriverId(
                        driverId
                );

        return moneyList.stream()
                .map(
                        money ->
                                new LedgerResponse(
                                        money.getPaymentId(),
                                        money.getAmount(),
                                        money.getApprovedAt()
                                )
                )
                .toList();
    }
}