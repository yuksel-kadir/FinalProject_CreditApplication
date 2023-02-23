package com.patika.creditapplication.service;

import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Application;
import com.patika.creditapplication.entity.Client;
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

    //NewClient newClient, Integer clientCreditScore, Application newApplication
    @Transactional
    public Client addClient(Client newClient) {
        Client client = findClientByIdentityOrPhoneNumber(
                newClient.getIdentityNumber(),
                newClient.getPhoneNumber());
        if (client == null) {
            log.info("Adding new client: {}", newClient);
            return clientRepository.save(newClient);
        }
        throw new ClientExistsException();
    }

    public Client getClientObject(NewClient newClient, Integer creditScore, Application creditApplication){
        return Client.builder()
                .firstName(newClient.getFirstName())
                .lastName(newClient.getLastName())
                .identityNumber(newClient.getIdentityNumber())
                .phoneNumber(newClient.getPhoneNumber())
                .dateOfBirth(newClient.getDateOfBirth())
                .creditScore(creditScore)
                .application(creditApplication)
                .build();
    }
    @Transactional
    public void deleteClient(String identityNumber) {
        Client client = clientRepository.findClientByIdentityNumber(identityNumber);
        if (client == null)
            throw new ClientNotFoundException();
        System.out.println("Deleting: " + client);
        clientRepository.delete(client);
    }

    public Client findClientByIdentityNumberAndBirthDate(String identityNumber, LocalDate dob) {
        return clientRepository.findClientByIdentityNumberAndDateOfBirth(
                identityNumber, dob
        );
    }

    public Client findClientByIdentityOrPhoneNumber(String identity, String phoneNumber){
        return clientRepository.findClientByIdentityNumberOrPhoneNumber(identity, phoneNumber);
    }
    /*
    public boolean doesClientExist(String identity, String phoneNumber){
        Client client = findClientByIdentityOrPhoneNumber(identity, phoneNumber);
        return client != null;
    }
    */
}
