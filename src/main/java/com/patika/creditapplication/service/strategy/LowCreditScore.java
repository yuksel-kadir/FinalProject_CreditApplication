package com.patika.creditapplication.service.strategy;

import org.springframework.stereotype.Component;

@Component
public class LowCreditScore implements CreditStrategy{

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        return 0f;
    }

    @Override
    public boolean isSuitableForCredit(Integer creditScore, Float monthlyIncome) {
        return false;
    }
}
