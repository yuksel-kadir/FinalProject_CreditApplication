package com.patika.creditapplication.service.strategy;

import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Application;
import com.patika.creditapplication.enums.CreditStatus;
import com.patika.creditapplication.exception.CreditStrategyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StrategyContextTest {

    private StrategyContext strategyContext;

    @BeforeEach
    void setUp() {
        strategyContext = new StrategyContext(
                Arrays.asList(
                        new LowCreditScore(),
                        new LowIncome(),
                        new MediumIncome(),
                        new HighCreditScore(),
                        new HighIncome()
                )
        );
    }

    @Test
    void shouldGetApplicationForNewClient() {
        NewClient client = NewClient.builder()
                .monthlyIncome(6000f)
                .collateral(1003.5f)
                .build();
        CreditStrategyResult
                credit = strategyContext.getCreditStrategyResult(
                client,
                555
        );
        Application application = Application.builder()
                .creditLimit(credit.getCreditLimit())
                .collateral(1003.5f)
                .isApproved(credit.getCreditStatus())
                .build();
        Application createdApplication =
                strategyContext.getApplicationForNewClient(client, 555);
        assertEquals(application, createdApplication);
    }

    @Test
    void shouldFindCreditStrategyWhenCreditScoreIsLow() {
        CreditStrategy creditStrategy = strategyContext.findCreditStrategy(100f, 499);
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
    void shouldGetCreditStrategyResultWhenCreditStrategyMediumIncome() {
        NewClient client = NewClient.builder()
                .monthlyIncome(6000f)
                .collateral(1000f)
                .build();
        Float creditlimit = 20000 + (1000f * 0.2f);
        CreditStrategyResult customResult = CreditStrategyResult.builder()
                .creditLimit(creditlimit)
                .creditStatus(CreditStatus.APPROVED)
                .build();
        CreditStrategyResult result = strategyContext.getCreditStrategyResult(client, 550);

        assertEquals(customResult, result);
    }
}