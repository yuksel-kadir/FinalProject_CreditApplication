package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.constant.CreditMultiplierConstant;

public class HighIncome implements CreditStrategy{

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        float creditLimit = monthlyIncome * (CreditMultiplierConstant.CREDIT_LIMIT_MULTIPLIER/2);
        if(collateral > 0)
            return creditLimit + collateral * 0.25f;
        return creditLimit;
    }

    @Override
    public boolean isSuitableForCredit(Integer creditScore, Float monthlyIncome) {
        return true;
    }
}
