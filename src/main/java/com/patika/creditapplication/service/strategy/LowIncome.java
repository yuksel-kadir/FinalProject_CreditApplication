package com.patika.creditapplication.service.strategy;

import org.springframework.stereotype.Component;

@Component
public class LowIncome implements CreditStrategy {

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        if (collateral > 0) {
            return 10000 + (collateral * 0.1f);
        }
        return 10000f;
    }

    @Override
    public boolean isSuitableForCredit(Integer creditScore, Float monthlyIncome) {
        return true;
    }
}
