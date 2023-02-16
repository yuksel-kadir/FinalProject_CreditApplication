package com.patika.creditapplication.service;

import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.exception.ClientExistsException;
import com.patika.creditapplication.repository.ClientRepository;
import com.patika.creditapplication.response.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ClientService {
    private final CreditScoreService creditScoreService;
    private final ClientRepository clientRepository;

    public ResponseData RegisterClient(NewClient newClient) {
        Client client = findClientByIdentificationNumberAndBirthDate(
                newClient.getIdentityNumber(),
                newClient.getDateOfBirth()
        );

        if (client == null) {
            int score = creditScoreService.getCreditScore();
            System.out.println("CLIENT CREDIT SCORE: " + score);
            Client client1 = Client.builder()
                    .firstName(newClient.getFirstName())
                    .lastName(newClient.getLastName())
                    .identityNumber(newClient.getIdentityNumber())
                    .phoneNumber(newClient.getPhoneNumber())
                    .dateOfBirth(newClient.getDateOfBirth())
                    .creditScore(score)
                    .monthlyIncome(newClient.getMonthlyIncome())
                    .collateral(newClient.getCollateral())
                    .build();
            clientRepository.save(client1);
            return new ResponseData(200, "User added.", newClient);
        }
        throw new ClientExistsException("Client registered already.");
    }

    public Client findClientByIdentificationNumberAndBirthDate(String identityNumber, LocalDate dob) {
        return clientRepository.findClientByIdentityNumberAndDateOfBirth(
                identityNumber, dob
        );
    }
}
