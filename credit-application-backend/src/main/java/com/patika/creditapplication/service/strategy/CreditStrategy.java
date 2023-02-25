package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.dto.response.CreditStatusBase;

public interface CreditStrategy {
    Float calculateCreditLimit(Float monthlyIncome, Float collateral);
    boolean isSuitableStrategy(Integer creditScore, Float monthlyIncome);

    CreditStatusBase getCreditStatus();
}
