package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.constant.CreditMultiplierConstant;
import com.patika.creditapplication.enums.CreditStatus;
import org.springframework.stereotype.Component;

@Component
public class HighIncome implements CreditStrategy{

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        float creditLimit = monthlyIncome * (CreditMultiplierConstant.CREDIT_LIMIT_MULTIPLIER/2f);
        if(collateral > 0)
            return creditLimit + collateral * 0.25f;
        return creditLimit;
    }

    @Override
    public boolean isSuitableStrategy(Integer creditScore, Float monthlyIncome) {
        return creditScore >= 500 && creditScore < 1000 && monthlyIncome > 10000;
    }

    @Override
    public CreditStatus getCreditStatus() {
        return CreditStatus.APPROVED;
    }
}
