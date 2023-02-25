package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.dto.response.CreditStatusBase;
import com.patika.creditapplication.dto.response.RejectedCreditApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LowCreditScore implements CreditStrategy {
    private final RejectedCreditApplication rejectedCreditApplication;

    @Override
    public Float calculateCreditLimit(Float monthlyIncome, Float collateral) {
        return 0f;
    }

    @Override
    public boolean isSuitableStrategy(Integer creditScore, Float monthlyIncome) {
        return creditScore < 500;
    }

    @Override
    public CreditStatusBase getCreditStatus() {
        return rejectedCreditApplication;
    }
}
