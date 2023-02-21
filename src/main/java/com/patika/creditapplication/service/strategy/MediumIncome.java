package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.enums.CreditStatus;
import org.springframework.stereotype.Component;

@Component
public class MediumIncome implements CreditStrategy {

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        if (collateral > 0) {
            return 20000 + (collateral * 0.2f);
        }
        return 20000f;
    }

    @Override
    public boolean isSuitableStrategy(Integer creditScore, Float monthlyIncome) {

        return creditScore >= 500 && creditScore < 1000 && monthlyIncome >= 5000 && monthlyIncome <= 10000;
    }

    @Override
    public CreditStatus getCreditStatus() {
        return CreditStatus.APPROVED;
    }
}
