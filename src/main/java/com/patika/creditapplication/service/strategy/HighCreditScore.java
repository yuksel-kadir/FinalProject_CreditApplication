package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.constant.CreditMultiplierConstant;

public class HighCreditScore implements CreditStrategy {
    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        float creditLimit = monthlyIncome * CreditMultiplierConstant.CREDIT_LIMIT_MULTIPLIER;
        if (collateral > 0)
            return (collateral * 0.5f) + creditLimit;
        return creditLimit;
    }

    @Override
    public boolean isSuitableForCredit(Integer creditScore, Float monthlyIncome) {
        return true;
    }
}
