package com.example.eazyadj.Controller;

import com.example.eazyadj.Dto.LedgerResponse;
import com.example.eazyadj.Service.MoneyService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LedgerController {

    private final MoneyService moneyService;

    public LedgerController(
            MoneyService moneyService
    ) {
        this.moneyService = moneyService;
    }

    @GetMapping("/ledger")
    public List<LedgerResponse> getLedger(

            @RequestParam("driver_id")
            Long driverId

    ) {

        return moneyService.getLedger(
                driverId
        );
    }
}