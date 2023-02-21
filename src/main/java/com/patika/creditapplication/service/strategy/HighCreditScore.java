package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.constant.CreditMultiplierConstant;
import com.patika.creditapplication.enums.CreditStatus;
import org.springframework.stereotype.Component;

@Component
public class HighCreditScore implements CreditStrategy {
    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        float creditLimit = monthlyIncome * CreditMultiplierConstant.CREDIT_LIMIT_MULTIPLIER;
        if (collateral > 0)
            return (collateral * 0.5f) + creditLimit;
        return creditLimit;
    }

    @Override
    public boolean isSuitableStrategy(Integer creditScore, Float monthlyIncome) {
        return creditScore >= 1000;
    }

    @Override
    public CreditStatus getCreditStatus() {
        return CreditStatus.APPROVED;
    }
}
