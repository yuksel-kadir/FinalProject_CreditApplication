package com.patika.creditapplication.service;

import com.patika.creditapplication.dto.ClientInformation;
import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.request.NewClient;
import com.patika.creditapplication.dto.response.CreditApplicationResult;
import com.patika.creditapplication.dto.response.CreditApplicationUpdateResult;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.entity.CreditApplication;
import com.patika.creditapplication.exception.CreditApplicationNotFoundException;
import com.patika.creditapplication.repository.CreditApplicationRepository;
import com.patika.creditapplication.service.strategy.StrategyContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.patika.creditapplication.util.CreditResultUtil.APPROVED;
import static com.patika.creditapplication.util.CreditResultUtil.REJECTED;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {
    private final CreditApplicationRepository creditApplicationRepository;
    private final StrategyContext strategyContext;


    @Transactional
    public CreditApplication addCreditApplication(CreditApplication creditApplication) {
        log.info("Adding credit application: {}", creditApplication);
        return creditApplicationRepository.save(creditApplication);
    }

    public CreditStrategyResult getStrategyResult(NewClient newClient, Integer creditScore) {
        return strategyContext.getCreditStrategyResult(newClient.getMonthlyIncome(), newClient.getCollateral(), creditScore);
    }

    public CreditStrategyResult getStrategyResult(ClientInformation params) {
        return strategyContext.getCreditStrategyResult(
                params.getNewMonthlyIncome(),
                params.getNewCollateral(),
                params.getCreditScore()
        );
    }

    public CreditApplicationResult getCreditApplicationResult(NewClient newClient, CreditStrategyResult creditStrategyResult) {
        return CreditApplicationResult.builder()
                .firstName(newClient.getFirstName())
                .lastName(newClient.getLastName())
                .identityNumber(newClient.getIdentityNumber())
                .creditLimit(creditStrategyResult.getCreditLimit())
                .creditScore(creditStrategyResult.getCreditScore())
                .creditStatus(creditStrategyResult.getCreditStatus().getStatusText())
                .build();
    }

    public CreditApplicationResult getCreditApplicationResult(Client client) {
        CreditApplication creditApplication = client.getCreditApplication();
        String creditStatus = creditApplication.getIsApproved() == 0 ? REJECTED : APPROVED;
        return CreditApplicationResult.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .identityNumber(client.getIdentityNumber())
                .creditScore(client.getCreditScore())
                .creditLimit(creditApplication.getCreditLimit())
                .creditStatus(creditStatus)
                .build();
    }

    public CreditApplicationUpdateResult getCreditApplicationResult(CreditApplication application){
        String creditStatus = application.getIsApproved() == 0 ? REJECTED : APPROVED;
        return CreditApplicationUpdateResult.builder()
                .creditStatus(creditStatus)
                .creditLimit(application.getCreditLimit())
                .build();
    }
    public CreditApplication getApplicationObjectForNewClient(CreditStrategyResult creditStrategyResult) {
        return CreditApplication.builder()
                .creditLimit(creditStrategyResult.getCreditLimit())
                .collateral(creditStrategyResult.getCollateral())
                .isApproved(creditStrategyResult.getCreditStatus().getStatusCode())
                .build();
    }

    public CreditApplicationUpdateResult updateCreditApplication(ClientInformation params) {
        CreditApplication application = findApplicationById(params.getCreditApplicationId());
        CreditStrategyResult result = getStrategyResult(params);
        application.setCollateral(result.getCollateral());
        application.setCreditLimit(result.getCreditLimit());
        application.setIsApproved(result.getCreditStatus().getStatusCode());
        CreditApplication updatedCreditApplication = addCreditApplication(application);
        return getCreditApplicationResult(updatedCreditApplication);
    }

    public CreditApplication findApplicationById(Long id) {
        CreditApplication application = creditApplicationRepository.findCreditApplicationById(id);
        if(application == null)
            throw new CreditApplicationNotFoundException();
        return application;
    }
}
