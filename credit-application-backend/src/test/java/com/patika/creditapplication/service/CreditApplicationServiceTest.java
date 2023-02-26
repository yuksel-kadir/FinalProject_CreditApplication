package com.patika.creditapplication.service;

import com.patika.creditapplication.dto.ClientInformation;
import com.patika.creditapplication.dto.CreditStrategyResult;
import com.patika.creditapplication.dto.request.NewClient;
import com.patika.creditapplication.dto.response.ApprovedCreditApplication;
import com.patika.creditapplication.dto.response.CreditApplicationResult;
import com.patika.creditapplication.dto.response.CreditApplicationUpdateResult;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.entity.CreditApplication;
import com.patika.creditapplication.exception.CreditApplicationNotFoundException;
import com.patika.creditapplication.repository.CreditApplicationRepository;
import com.patika.creditapplication.service.strategy.StrategyContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.patika.creditapplication.util.CreditResultUtil.APPROVED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditApplicationServiceTest {

    @Mock
    private CreditApplicationRepository creditApplicationRepository;
    @Mock
    private StrategyContext strategyContext;
    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void shouldgetCreditApplicationResult_OverloadWithNewClientAndCreditApplicationResult() {
        CreditStrategyResult result = CreditStrategyResult.builder()
                .creditLimit(1f)
                .creditScore(1)
                .creditStatus(new ApprovedCreditApplication())
                .build();
        CreditApplicationResult appResult = CreditApplicationResult.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("1111111111")
                .creditLimit(1f)
                .creditScore(1)
                .creditStatus(APPROVED)
                .build();
        NewClient client = NewClient.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("1111111111")
                .build();
        CreditApplicationResult createdAppResult = applicationService.getCreditApplicationResult(client, result);
        assertEquals(
                createdAppResult.getFirstName(),
                appResult.getFirstName()
        );
        assertEquals(
                createdAppResult.getLastName(),
                appResult.getLastName()
        );
        assertEquals(
                createdAppResult.getIdentityNumber(),
                appResult.getIdentityNumber()
        );
        assertEquals(
                createdAppResult.getCreditLimit(),
                appResult.getCreditLimit()
        );
        assertEquals(
                createdAppResult.getCreditScore(),
                appResult.getCreditScore()
        );
        assertEquals(
                createdAppResult.getCreditStatus(),
                appResult.getCreditStatus()
        );
    }

    @Test
    void shouldGetApplicationObjectForNewClient() {
        CreditStrategyResult strategyResult = CreditStrategyResult.builder()
                .collateral(1f)
                .creditLimit(1f)
                .creditStatus(new ApprovedCreditApplication())
                .build();
        CreditApplication application = CreditApplication.builder()
                .collateral(1f)
                .creditLimit(1f)
                .isApproved(1)
                .build();
        CreditApplication createdApplication = applicationService.getApplicationObjectForNewClient(strategyResult);
        assertEquals(application.getCreditLimit(), createdApplication.getCreditLimit());
        assertEquals(application.getIsApproved(), createdApplication.getIsApproved());
        assertEquals(application.getCollateral(), createdApplication.getCollateral());
    }

    @Test
    void shouldGetCreditApplicationResult_OverloadWithApplication() {
        CreditApplication application = CreditApplication.builder()
                .isApproved(1)
                .creditLimit(1f)
                .build();
        CreditApplicationUpdateResult result = CreditApplicationUpdateResult.builder()
                .creditLimit(1f)
                .creditStatus(APPROVED)
                .build();
        CreditApplicationUpdateResult createdResult = applicationService.getCreditApplicationResult(application);
        assertEquals(createdResult.getCreditLimit(), result.getCreditLimit());
        assertEquals(createdResult.getCreditStatus(), result.getCreditStatus());
    }

    @Test
    void shouldFindApplicationByIdThrowsException(){
        when(creditApplicationRepository.findCreditApplicationById(1L)).thenReturn(null);
        assertThrows(
                CreditApplicationNotFoundException.class,
                () -> applicationService.findApplicationById(1L)
        );
    }

    @Test
    void shouldGetCreditApplicationResult(){

        CreditApplication application = CreditApplication.builder()
                .creditLimit(1f)
                .isApproved(1)
                .build();
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("11111111111")
                .creditScore(1)
                .creditApplication(application)
                .build();
        CreditApplicationResult result = CreditApplicationResult.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .identityNumber(client.getIdentityNumber())
                .creditScore(client.getCreditScore())
                .creditLimit(application.getCreditLimit())
                .creditStatus(APPROVED)
                .build();
        CreditApplicationResult createdResult = applicationService.getCreditApplicationResult(client);
        assertEquals(createdResult.getCreditStatus(), result.getCreditStatus());
        assertEquals(createdResult.getCreditLimit(), result.getCreditLimit());
        assertEquals(createdResult.getFirstName(), result.getFirstName());
        assertEquals(createdResult.getCreditScore(), result.getCreditScore());
    }
    /*
    @Test
    @Disabled
    void shouldAddCreditApplication() {
        CreditApplication creditApplication = CreditApplication.builder()
                .build();
        applicationService.addCreditApplication(creditApplication);
        ArgumentCaptor<CreditApplication> applicationArgumentCaptor = ArgumentCaptor
                .forClass(CreditApplication.class);
        verify(creditApplicationRepository).save(applicationArgumentCaptor.capture());
        CreditApplication createdCreditApplication = applicationArgumentCaptor.getValue();
        assertEquals(createdCreditApplication, creditApplication);
    }

    @Test
    @Disabled
    void shouldProcessCreditApplicationThrowsExceptionWhenCreditScoreLow() {
        NewClient client = NewClient.builder()
                .monthlyIncome(6000f)
                .collateral(1000f)
                .build();
        applicationService.createApplicationForNewClient(client, 499);
    }

    @Test
    @Disabled
    void shouldCreateCreditApplicationResult(){
        CreditApplication creditApplication = CreditApplication.builder()
                .creditLimit(1500f)
                .isApproved(CreditStatus.APPROVED)
                .build();
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("43945465268")
                .creditScore(567)
                .creditApplication(creditApplication)
                .build();
        //applicationService.createCreditApplicationResult(client);
    }

    @Test
    @Disabled
    void findApplicationByIdentityNumberAndDateOfBirth() {
        when(clientService.findClientByIdentityNumberAndBirthDate(
                        "43945465268",
                        LocalDate.of(1997, 10, 31)
                )
        )
                .thenReturn(null);

        assertThrows(ClientNotFoundException.class, () ->
                applicationService.findApplicationByIdentityNumberAndDateOfBirth(
                        "43945465268",
                        LocalDate.of(1997, 10, 31)
                )
        );

    }
    */
}