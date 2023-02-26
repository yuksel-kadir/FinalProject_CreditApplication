package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.request.NewClient;
import com.patika.creditapplication.dto.response.ApprovedCreditApplication;
import com.patika.creditapplication.dto.response.RejectedCreditApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StrategyContextTest {

    @InjectMocks
    private StrategyContext strategyContext;

    @BeforeEach
    void setUp() {
        strategyContext = new StrategyContext(
                Arrays.asList(
                        new LowCreditScore(new RejectedCreditApplication()),
                        new LowIncome(new ApprovedCreditApplication()),
                        new MediumIncome(new ApprovedCreditApplication()),
                        new HighCreditScore(new ApprovedCreditApplication()),
                        new HighIncome(new ApprovedCreditApplication())
                )
        );
    }

    @Test
    void shouldFindCreditStrategyWhenCreditScoreIsLow() {
        CreditStrategy creditStrategy = strategyContext
                .findCreditStrategy(100f, 499);
        assertEquals(LowCreditScore.class, creditStrategy.getClass());
    }

    @Test
    void shouldFindCreditStrategyWhenIncomeIsLow() {
        CreditStrategy creditStrategy =
                strategyContext.findCreditStrategy(4999f, 559);
        assertEquals(LowIncome.class, creditStrategy.getClass());
    }

    @Test
    void shouldFindCreditStrategyWhenIncomeIsMedium() {
        CreditStrategy creditStrategy =
                strategyContext.findCreditStrategy(5000f, 559);
        assertEquals(MediumIncome.class, creditStrategy.getClass());
    }

    @Test
    void shouldFindCreditStrategyWhenIncomeIsHigh() {
        CreditStrategy creditStrategy =
                strategyContext.findCreditStrategy(50000f, 559);
        assertEquals(HighIncome.class, creditStrategy.getClass());
    }

    @Test
    void shouldFindCreditStrategyWhenCreditScoreIsHigh() {
        CreditStrategy creditStrategy =
                strategyContext.findCreditStrategy(1000f, 1000);
        assertEquals(HighCreditScore.class, creditStrategy.getClass());
    }

    @Test
    void shouldGetCreditStrategyResultWhenCreditStrategyMediumIncome() {
        NewClient client = NewClient.builder()
                .monthlyIncome(6000f)
                .collateral(1000f)
                .build();
        Float creditlimit = 20000 + (1000f * 0.2f);
        CreditStrategyResult customResult = CreditStrategyResult.builder()
                .creditLimit(creditlimit)
                .creditStatus(new ApprovedCreditApplication())
                .build();
        CreditStrategyResult result = strategyContext.getCreditStrategyResult(
                client.getMonthlyIncome(),
                client.getCollateral(),
                550
        );

        assertEquals(customResult.getCreditLimit(), result.getCreditLimit());
        assertEquals(customResult.getCreditStatus().getStatusCode(), result.getCreditStatus().getStatusCode());
    }

}