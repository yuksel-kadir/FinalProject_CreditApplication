package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.exception.CreditStrategyNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StrategyContext {
    private final List<CreditStrategy> creditStrategyList;

    public CreditStrategyResult getCreditStrategyResult(NewClient newClient, Integer clientCreditScore) {
        //Find the appropriate strategy
        CreditStrategy suitableCreditStrategy = creditStrategyList.stream()
                .filter(creditStrategy -> creditStrategy.isSuitableStrategy(
                        clientCreditScore,
                        newClient.getMonthlyIncome())
                )
                .findFirst()
                .orElseThrow(CreditStrategyNotFoundException::new);

        System.out.println("Credit Strategy Class: " + suitableCreditStrategy.getClass().toString());
        //Return a strategy result.
        return new CreditStrategyResult(
                suitableCreditStrategy.getCreditStatus(),
                suitableCreditStrategy.calculateCreditLimit(
                        newClient.getMonthlyIncome(),
                        newClient.getCollateral()
                )
        );
    }
}
