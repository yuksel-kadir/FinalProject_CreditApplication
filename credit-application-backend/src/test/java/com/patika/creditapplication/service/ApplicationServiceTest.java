package com.patika.creditapplication.service;

import com.patika.creditapplication.adapter.SMSSenderClient;
import com.patika.creditapplication.dto.NewClient;
import com.patika.creditapplication.entity.Application;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.enums.CreditStatus;
import com.patika.creditapplication.exception.ClientNotFoundException;
import com.patika.creditapplication.exception.CreditStrategyNotFoundException;
import com.patika.creditapplication.repository.CreditApplicationRepository;
import com.patika.creditapplication.service.strategy.StrategyContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private CreditApplicationRepository creditApplicationRepository;
    @Mock
    private CreditScoreService creditScoreService;
    @Mock
    private StrategyContext strategyContext;
    @Mock
    private SMSSenderClient smsSenderClient;
    @Mock
    private ClientService clientService;
    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void shouldAddCreditApplication() {
        Application application = Application.builder()
                .build();
        applicationService.addCreditApplication(application);
        ArgumentCaptor<Application> applicationArgumentCaptor = ArgumentCaptor
                .forClass(Application.class);
        verify(creditApplicationRepository).save(applicationArgumentCaptor.capture());
        Application createdApplication = applicationArgumentCaptor.getValue();
        assertEquals(createdApplication, application);
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
    void shouldCreateCreditApplicationResult(){
        Application application = Application.builder()
                .creditLimit(1500f)
                .isApproved(CreditStatus.APPROVED)
                .build();
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("43945465268")
                .creditScore(567)
                .application(application)
                .build();
        applicationService.createCreditApplicationResult(client);

    }

    @Test
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

}