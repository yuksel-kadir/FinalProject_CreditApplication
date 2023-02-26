package com.patika.creditapplication.service;

import com.patika.creditapplication.smssystem.SMSSenderClient;
import com.patika.creditapplication.dto.ClientInformation;
import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.request.ClientUpdateNameParam;
import com.patika.creditapplication.dto.request.ClientUpdatePhoneNumberParam;
import com.patika.creditapplication.dto.request.NewClient;
import com.patika.creditapplication.dto.response.CreditApplicationResult;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.entity.CreditApplication;
import com.patika.creditapplication.enums.Carrier;
import com.patika.creditapplication.exception.ClientExistsException;
import com.patika.creditapplication.exception.ClientNotFoundException;
import com.patika.creditapplication.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final CreditScoreService creditScoreService;
    private final ApplicationService applicationService;
    private final SMSSenderClient smsSenderClient;

    @Transactional
    public Client addClient(Client newClient) {
        Client client = findClientByIdentityOrPhoneNumber(newClient.getIdentityNumber(), newClient.getPhoneNumber());
        if (client == null) {
            log.info("Adding new client: {}", newClient);
            return clientRepository.save(newClient);
        }
        throw new ClientExistsException();
    }

    public CreditApplicationResult processCreditApplication(NewClient newClient) {
        log.info("Processing credit application for: {}", newClient);
        Integer creditScore = creditScoreService.getCreditScore();
        CreditStrategyResult strategyResult = applicationService.getStrategyResult(newClient, creditScore);

        CreditApplication newCreditApplication = applicationService.getApplicationObjectForNewClient(strategyResult);
        Client clientToAdd = getClientObject(newClient, creditScore, newCreditApplication);
        Client client = addClient(clientToAdd);
        log.info("New client: {} added to the database.", client);

        CreditApplicationResult applicationResult = applicationService.getCreditApplicationResult(newClient, strategyResult);
        String smsMessage = "Your credit application result -> Credit Limit: " + applicationResult.getCreditLimit() +
                " Credit Status: " + applicationResult.getCreditStatus();
        smsSenderClient.sendSMS(Carrier.TURKCELL, smsMessage, client.getPhoneNumber());
        return applicationResult;
    }


    public Client getClientObject(NewClient newClient, Integer creditScore, CreditApplication creditApplication) {
        return Client.builder()
                .firstName(newClient.getFirstName())
                .lastName(newClient.getLastName())
                .identityNumber(newClient.getIdentityNumber())
                .phoneNumber(newClient.getPhoneNumber())
                .dateOfBirth(newClient.getDateOfBirth())
                .monthlyIncome(newClient.getMonthlyIncome())
                .creditScore(creditScore)
                .creditApplication(creditApplication)
                .build();
    }

    @Transactional
    public void deleteClient(String identityNumber) {
        Client client = clientRepository.findClientByIdentityNumber(identityNumber);
        if (client == null)
            throw new ClientNotFoundException();
        clientRepository.delete(client);
        log.info("Client Deleted : {}", client);
    }

    public Client findClientByIdentityNumberAndBirthDate(String identityNumber, LocalDate dob) {
        return clientRepository.findClientByIdentityNumberAndDateOfBirth(
                identityNumber, dob
        );
    }

    public Client findClientByIdentityOrPhoneNumber(String identity, String phoneNumber) {
        return clientRepository.findClientByIdentityNumberOrPhoneNumber(identity, phoneNumber);
    }

    public CreditApplicationResult findApplicationByIdentityNumberAndDateOfBirth(String identity, LocalDate dob) {
        Client client = findClientByIdentityNumberAndBirthDate(identity, dob);
        if (client == null)
            throw new ClientNotFoundException();
        return applicationService.getCreditApplicationResult(client);
    }

    @Transactional
    public String updateClientFirstName(ClientUpdateNameParam params){
        Client client = clientRepository.findClientByIdentityNumber(params.getIdentityNumber());
        if(client == null)
            throw new ClientNotFoundException();
        client.setFirstName(params.getInput());
        Client changedClient = clientRepository.save(client);
        log.info("Changed client name from {} to {}", client.getFirstName(), changedClient.getFirstName());
        return changedClient.getFirstName() + " " + changedClient.getLastName();
    }

    @Transactional
    public String updateClientLastName(ClientUpdateNameParam params){
        Client client = clientRepository.findClientByIdentityNumber(params.getIdentityNumber());
        if(client == null)
            throw new ClientNotFoundException();
        client.setLastName(params.getInput());
        Client changedClient = clientRepository.save(client);
        log.info("Changed client last name from {} to {}", params.getInput(), client.getLastName());
        return changedClient.getFirstName() + " " + changedClient.getLastName();
    }

    @Transactional
    public String updateClientPhoneNumber(ClientUpdatePhoneNumberParam params){
        Client client = clientRepository.findClientByIdentityNumber(params.getIdentityNumber());
        if(client == null)
            throw new ClientNotFoundException();
        client.setPhoneNumber(params.getPhoneNumber());
        Client changedClient = clientRepository.save(client);
        log.info("Changed client phone number from {} to {}", params.getPhoneNumber(), changedClient.getPhoneNumber());
        return changedClient.getPhoneNumber();
    }

    public ClientInformation getClientInformation(String identityNumber){
        Client client = clientRepository.findClientByIdentityNumber(identityNumber);
        if(client == null)
            throw new ClientNotFoundException();
        return ClientInformation.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .creditApplicationId(client.getCreditApplication().getId())
                .creditScore(client.getCreditScore())
                .build();
    }
}
