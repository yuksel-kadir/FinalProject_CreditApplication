package com.patika.creditapplication.service;

import com.patika.creditapplication.dto.CreditApplicationResult;
import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Application;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.exception.ClientExistsException;
import com.patika.creditapplication.repository.CreditApplicationRepository;
import com.patika.creditapplication.response.Response;
import com.patika.creditapplication.response.ResponseData;
import com.patika.creditapplication.service.strategy.StrategyContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class ApplicationService {
    private final CreditApplicationRepository creditApplicationRepository;
    private final CreditScoreService creditScoreService;
    private final ClientService clientService;
    private final StrategyContext strategyContext;


    @Transactional
    public Application addCreditApplication(Application creditApplication) {
        return creditApplicationRepository.save(creditApplication);
    }

    //Process new credit application
    public Response processCreditApplication(NewClient newClient) {
        //Check if the client exists.
        if (
                clientService.doesClientExist(
                        newClient.getIdentityNumber(),
                        newClient.getPhoneNumber()
                )
        )
            throw new ClientExistsException();
        //Get new client's credit score.
        Integer creditScore = creditScoreService.getCreditScore();
        System.out.println("CREDIT SCORE: " + creditScore);
        //Find suitable credit strategy by monthly income and credit score.
        CreditStrategyResult creditStrategyResult = strategyContext.getCreditStrategyResult(newClient, creditScore);
        //Print credit limit.
        System.out.println("CREDIT LIMIT: " + creditStrategyResult.getCreditLimit());
        //Create a credit application object.
        Application newApplication = Application.builder()
                .creditLimit(creditStrategyResult.getCreditLimit())
                .collateral(newClient.getCollateral())
                .isApproved(creditStrategyResult.getCreditStatus())
                .build();
        //Add new application to the database.
        Application creditApplication = addCreditApplication(newApplication);
        //Add new client to the database.
        Client client = clientService.addClient(newClient, creditScore, creditApplication);
        //Create a credit application result object.
        CreditApplicationResult applicationResult = CreditApplicationResult.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .identityNumber(client.getIdentityNumber())
                .creditScore(client.getCreditScore())
                .creditLimit(creditApplication.getCreditLimit())
                .creditStatus(creditApplication.getIsApproved())
                .build();
        //Return the credit application result.
        return new ResponseData(
                200,
                "The credit application processed successfully.",
                applicationResult
        );

    }

    public Application getCreditApplicationById(Long id) {
        return creditApplicationRepository
                .findById(id)
                .orElse(null);
    }

}
