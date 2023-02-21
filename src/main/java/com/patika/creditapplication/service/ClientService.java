package com.patika.creditapplication.service;

import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Application;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.exception.ClientExistsException;
import com.patika.creditapplication.exception.ClientNotFoundException;
import com.patika.creditapplication.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Client addClient(
            NewClient newClient,
            Integer clientCreditScore,
            Application newApplication
    ) {

        Client client = findClientByIdentityNumberAndPhoneNumber(
                newClient.getIdentityNumber(),
                newClient.getPhoneNumber()
        );

        if (client == null) {
            System.out.println("CLIENT CREDIT SCORE: " + clientCreditScore);
            Client client1 = Client.builder()
                    .firstName(newClient.getFirstName())
                    .lastName(newClient.getLastName())
                    .identityNumber(newClient.getIdentityNumber())
                    .phoneNumber(newClient.getPhoneNumber())
                    .dateOfBirth(newClient.getDateOfBirth())
                    .creditScore(clientCreditScore)
                    .monthlyIncome(newClient.getMonthlyIncome())
                    .application(newApplication)
                    .build();

            return clientRepository.save(client1);
        }
        throw new ClientExistsException();
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

    public Client findClientByIdentityNumberAndPhoneNumber(
            String identity,
            String phoneNumber
    ){
        return clientRepository.findClientByIdentityNumberAndPhoneNumber(identity, phoneNumber);
    }

    public Client findClientByIdentityNumber(String identity){
        return clientRepository.findClientByIdentityNumber(identity);
    }

    public Client findClientByPhoneNumber(String phoneNumber){
        return clientRepository.findClientByPhoneNumber(phoneNumber);
    }

    public Client findClientByIdentityOrPhoneNumber(String identity, String phoneNumber){
        return clientRepository.findClientByIdentityNumberOrPhoneNumber(identity, phoneNumber);
    }
    public boolean doesClientExist(String identity, String phoneNumber){
        Client client = findClientByIdentityOrPhoneNumber(identity, phoneNumber);
        return client != null;
    }

}
