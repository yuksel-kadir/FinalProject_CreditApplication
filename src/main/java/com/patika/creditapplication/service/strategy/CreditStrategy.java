package com.patika.creditapplication.service.strategy;

public interface CreditStrategy {
    Float calculateCreditLimit(Float monthlyIncome, Float collateral);
    boolean isSuitableForCredit(Integer creditScore, Float monthlyIncome);
}
