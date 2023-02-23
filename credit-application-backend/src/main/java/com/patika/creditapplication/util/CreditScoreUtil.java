package com.patika.creditapplication.util;

import java.util.Random;

public final class CreditScoreUtil {
    private CreditScoreUtil() {
        throw new UnsupportedOperationException("This is a constant class and cannot be instantiated");
    }

    public static Integer generateRandomCreditScore() {
        Random random = new Random();
        int highestCreditScore = 1500;
        return random.nextInt(highestCreditScore);
    }
}
