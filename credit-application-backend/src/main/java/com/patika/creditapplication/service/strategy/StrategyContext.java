package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Application;
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

    public Application getApplicationForNewClient(NewClient newClient, Integer clientCreditScore) {
        CreditStrategyResult creditStrategyResult = getCreditStrategyResult(newClient, clientCreditScore);
        Float creditLimit = creditStrategyResult.getCreditLimit();
        log.info("Client's credit limit: {}", creditLimit);
        return Application.builder()
                .creditLimit(creditLimit)
                .collateral(newClient.getCollateral())
                .isApproved(creditStrategyResult.getCreditStatus())
                .build();
    }

    public CreditStrategy findCreditStrategy(Float monthlyIncome, Integer clientCreditScore) {
        return creditStrategyList.stream()
                .filter(creditStrategy -> creditStrategy.isSuitableStrategy(clientCreditScore, monthlyIncome))
                .findAny()
                .orElseThrow(CreditStrategyNotFoundException::new);
    }

    public CreditStrategyResult getCreditStrategyResult(NewClient newClient, Integer clientCreditScore) {
        CreditStrategy suitableCreditStrategy = findCreditStrategy(newClient.getMonthlyIncome(), clientCreditScore);
        log.info("Credit strategy: {}", suitableCreditStrategy.getClass().toString());
        return new CreditStrategyResult(
                suitableCreditStrategy.getCreditStatus(),
                suitableCreditStrategy.calculateCreditLimit(
                        newClient.getMonthlyIncome(),
                        newClient.getCollateral()
                )
        );
    }
}
