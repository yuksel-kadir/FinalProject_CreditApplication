package com.patika.creditapplication.service;

import com.patika.creditapplication.adapter.SMSSenderClient;
import com.patika.creditapplication.dto.CreditApplicationResult;
import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Application;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.enums.Carrier;
import com.patika.creditapplication.exception.ClientNotFoundException;
import com.patika.creditapplication.repository.CreditApplicationRepository;
import com.patika.creditapplication.service.strategy.StrategyContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {
    private final CreditApplicationRepository creditApplicationRepository;
    private final CreditScoreService creditScoreService;
    private final ClientService clientService;
    private final StrategyContext strategyContext;
    private final SMSSenderClient smsSenderClient;

    @Transactional
    public Application addCreditApplication(Application creditApplication) {
        log.info("Adding new credit application: {}", creditApplication);
        return creditApplicationRepository.save(creditApplication);
    }

    public Application createApplicationForNewClient(NewClient newClient, Integer creditScore){
        Application newApplication = strategyContext.getApplicationForNewClient(newClient, creditScore);
        return newApplication;
    }

    //Process new credit application
    public CreditApplicationResult processCreditApplication(NewClient newClient) {
        log.info("Processing credit application for: {}", newClient);
        Integer creditScore = creditScoreService.getCreditScore();
        log.info("Client's credit score: {}", creditScore);
        Application newApplication = createApplicationForNewClient(newClient, creditScore);
        Application creditApplication = addCreditApplication(newApplication);
        log.info("Credit application: {} added to the database.", creditApplication);
        Client clientObj = clientService.getClientObject(newClient, creditScore, creditApplication);
        Client client = clientService.addClient(clientObj);//newClient, creditScore, creditApplication
        log.info("New client: {} added to the database.", client);
        CreditApplicationResult applicationResult = createCreditApplicationResult(client);
        log.info("Application result is created: {}", applicationResult);
        String smsMessage = "Your credit application result -> Credit Limit: " + applicationResult.getCreditLimit() +
                " Credit Status: " + applicationResult.getCreditStatus();
        smsSenderClient.sendSMS(Carrier.TURKCELL, smsMessage, client.getPhoneNumber());
        return applicationResult;
    }

    public CreditApplicationResult createCreditApplicationResult(Client client) {
        Application application = client.getApplication();
        return CreditApplicationResult.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .identityNumber(client.getIdentityNumber())
                .creditScore(client.getCreditScore())
                .creditLimit(application.getCreditLimit())
                .creditStatus(application.getIsApproved())
                .build();
    }

    public CreditApplicationResult findApplicationByIdentityNumberAndDateOfBirth(String identity, LocalDate dob) {
        Client client = clientService.findClientByIdentityNumberAndBirthDate(identity, dob);
        if (client == null)
            throw new ClientNotFoundException();
        return createCreditApplicationResult(client);
    }

}
