package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.enums.CreditStatus;

public interface CreditStrategy {
    Float calculateCreditLimit(Float monthlyIncome, Float collateral);
    boolean isSuitableStrategy(Integer creditScore, Float monthlyIncome);

    CreditStatus getCreditStatus();
}
