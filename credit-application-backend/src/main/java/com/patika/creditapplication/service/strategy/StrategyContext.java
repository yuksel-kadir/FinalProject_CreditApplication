package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.request.NewClient;
import com.patika.creditapplication.exception.CreditStrategyNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class StrategyContext {
    private final List<CreditStrategy> creditStrategyList;

    public CreditStrategy findCreditStrategy(Float monthlyIncome, Integer clientCreditScore) {
        return creditStrategyList.stream()
                .filter(creditStrategy -> creditStrategy.isSuitableStrategy(clientCreditScore, monthlyIncome))
                .findAny()
                .orElseThrow(CreditStrategyNotFoundException::new);
    }

    public CreditStrategyResult getCreditStrategyResult(Float monthlyIncome, Float collateral, Integer clientCreditScore) {
        CreditStrategy suitableCreditStrategy = findCreditStrategy(monthlyIncome, clientCreditScore);
        log.info("Found Credit strategy: {}", suitableCreditStrategy.getClass().toString());
        Float creditLimit = suitableCreditStrategy.calculateCreditLimit(monthlyIncome, collateral);
        log.info("Client's credit limit: {}", creditLimit);
        return CreditStrategyResult.builder()
                .creditStatus(suitableCreditStrategy.getCreditStatus())
                .creditLimit(creditLimit)
                .collateral(collateral)
                .creditScore(clientCreditScore)
                .build();
    }
}
