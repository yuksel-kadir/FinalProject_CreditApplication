package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.enums.CreditStatus;
import org.springframework.stereotype.Component;

@Component
public class LowCreditScore implements CreditStrategy{

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        return 0f;
    }

    @Override
    public boolean isSuitableStrategy(Integer creditScore, Float monthlyIncome) {
        return creditScore < 500;
    }

    @Override
    public CreditStatus getCreditStatus() {
        return CreditStatus.REJECTED;
    }
}
