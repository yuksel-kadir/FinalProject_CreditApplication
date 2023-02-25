package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.constant.CreditMultiplierConstant;
import com.patika.creditapplication.dto.response.ApprovedCreditApplication;
import com.patika.creditapplication.dto.response.CreditStatusBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HighCreditScore implements CreditStrategy {

    private final ApprovedCreditApplication approvedCreditApplication;

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


    public CreditStatusBase getCreditStatus() {
        return approvedCreditApplication;
    }
}
