package com.patika.creditapplication.service.strategy;

import org.springframework.stereotype.Component;

@Component
public class MediumIncome implements CreditStrategy {

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        if (collateral > 0) {
            return 20000 + (20000 * 0.2f);
        }
        return 20000f;
    }

    @Override
    public boolean isSuitableForCredit(Integer creditScore, Float monthlyIncome) {
        return true;
    }
}
