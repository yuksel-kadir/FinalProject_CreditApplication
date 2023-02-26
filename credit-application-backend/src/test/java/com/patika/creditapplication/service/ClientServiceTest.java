package com.patika.creditapplication.service;

import com.patika.creditapplication.dto.ClientInformation;
import com.patika.creditapplication.dto.request.ClientUpdateNameParam;
import com.patika.creditapplication.dto.request.ClientUpdatePhoneNumberParam;
import com.patika.creditapplication.dto.request.NewClient;
import com.patika.creditapplication.dto.response.CreditApplicationResult;
import com.patika.creditapplication.entity.Client;
import com.patika.creditapplication.entity.CreditApplication;
import com.patika.creditapplication.exception.ClientExistsException;
import com.patika.creditapplication.exception.ClientNotFoundException;
import com.patika.creditapplication.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ApplicationService applicationService;
    @InjectMocks
    private ClientService clientService;


    @Test
    void shouldAddClientWhenClientDoesntExist() {
        Client client1 = Client.builder()
                .id(1L)
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("43945465268")
                .phoneNumber("05079788883")
                .dateOfBirth(LocalDate.of(1997, 10, 31))
                .creditScore(550)
                .monthlyIncome(3050f)
                .creditApplication(CreditApplication.builder().id(1L).build())
                .build();


        when(clientService.findClientByIdentityOrPhoneNumber(
                client1.getIdentityNumber(), client1.getPhoneNumber()))
                .thenReturn(null);

        clientService.addClient(client1);

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor
                .forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());

        Client createdClient = clientArgumentCaptor.getValue();
        assertEquals(createdClient, client1);
        assertEquals("Kadir", createdClient.getFirstName());
        assertEquals("Yüksel", createdClient.getLastName());
        assertEquals("43945465268", createdClient.getIdentityNumber());
        assertEquals("05079788883", createdClient.getPhoneNumber());
    }

    @Test
    void shouldAddClientThrowsExceptionWhenClientExists() {
        Client client = Client.builder()
                .identityNumber("43945465268")
                .phoneNumber("05079788883")
                .build();
        when(clientService.findClientByIdentityOrPhoneNumber(client.getIdentityNumber(), client.getPhoneNumber()))
                .thenReturn(new Client());
        assertThrows(
                ClientExistsException.class,
                () -> clientService.addClient(client)
        );
    }

    @Test
    void shouldDeleteClientThrowsExceptionWhenClientDoesntExist() {
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(null);
        assertThrows(
                ClientNotFoundException.class,
                () -> clientService.deleteClient("11111111111")
        );
    }

    @Test
    void shouldDeleteClientWhenExists() {
        CreditApplication creditApplication = CreditApplication.builder().id(1L).build();
        Client client = Client.builder().id(1L).creditApplication(creditApplication).build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(client);
        clientService.deleteClient("11111111111");
        verify(clientRepository).delete(client);
    }

    @Test
    void shouldUpdateClientFirstNameThrowsExceptionWhenClientDoesntExist() {
        ClientUpdateNameParam params = ClientUpdateNameParam.builder().input("Name").identityNumber("11111111111").build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(null);
        assertThrows(
                ClientNotFoundException.class,
                () -> clientService.updateClientFirstName(params)
        );
    }

    @Test
    void shouldUpdateClientLastNameThrowsExceptionWhenClientDoesntExist() {
        ClientUpdateNameParam params = ClientUpdateNameParam.builder().input("LastName").identityNumber("11111111111").build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(null);
        assertThrows(
                ClientNotFoundException.class,
                () -> clientService.updateClientLastName(params)
        );
    }

    @Test
    void shouldUpdateClientPhoneNumberThrowsExceptionWhenClientDoesntExist() {
        ClientUpdatePhoneNumberParam params = ClientUpdatePhoneNumberParam.builder().phoneNumber("05555555555").identityNumber("11111111111").build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(null);
        assertThrows(
                ClientNotFoundException.class,
                () -> clientService.updateClientPhoneNumber(params)
        );
    }

    @Test
    void shouldUpdateClientFirstNameWhenClientExists() {
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .build();
        Client changedClient = Client.builder()
                .firstName("NewName")
                .lastName("Yüksel")
                .build();
        ClientUpdateNameParam params = ClientUpdateNameParam.builder()
                .input("NewName")
                .identityNumber("11111111111")
                .build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(client);
        when(clientRepository.save(changedClient)).thenReturn(changedClient);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor
                .forClass(Client.class);
        clientService.updateClientFirstName(params);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client createdClient = clientArgumentCaptor.getValue();
        assertEquals(createdClient.getFirstName(), params.getInput());
        assertEquals(createdClient.getLastName(), changedClient.getLastName());
    }

    @Test
    void shouldUpdateClientLastNameWhenClientExists() {
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .build();
        Client changedClient = Client.builder()
                .firstName("Kadir")
                .lastName("NewLastName")
                .build();
        ClientUpdateNameParam params = ClientUpdateNameParam.builder()
                .input("NewLastName")
                .identityNumber("11111111111")
                .build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(client);
        when(clientRepository.save(changedClient)).thenReturn(changedClient);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor
                .forClass(Client.class);
        clientService.updateClientLastName(params);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client createdClient = clientArgumentCaptor.getValue();
        assertEquals(createdClient.getFirstName(), changedClient.getFirstName());
        assertEquals(createdClient.getLastName(), params.getInput());
    }

    @Test
    void shouldUpdateClientPhoneWhenClientExists() {
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .phoneNumber("05444444444")
                .build();
        Client changedClient = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .phoneNumber("05555555555")
                .build();
        ClientUpdatePhoneNumberParam params = ClientUpdatePhoneNumberParam.builder()
                .phoneNumber("05555555555")
                .identityNumber("11111111111")
                .build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(client);
        when(clientRepository.save(changedClient)).thenReturn(changedClient);
        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor
                .forClass(Client.class);
        clientService.updateClientPhoneNumber(params);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        Client createdClient = clientArgumentCaptor.getValue();
        assertEquals(createdClient.getFirstName(), changedClient.getFirstName());
        assertEquals(createdClient.getLastName(), changedClient.getLastName());
        assertEquals(changedClient.getPhoneNumber(), params.getPhoneNumber());
    }

    @Test
    void shouldFindApplicationByIdentityAndDateOfBirthThrowsExceptionWhenClientDoesntExist() {
        when(clientService
                .findClientByIdentityNumberAndBirthDate("11111111111", LocalDate.now())
        ).thenReturn(null);
        assertThrows(
                ClientNotFoundException.class,
                () -> clientService.findApplicationByIdentityNumberAndDateOfBirth("11111111111", LocalDate.now())
        );
    }

    @Test
    void shouldFindApplicationByIdentityAndDateOfBirthWhenClientExists() {
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .build();
        CreditApplicationResult result = CreditApplicationResult.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .build();
        when(clientService
                .findClientByIdentityNumberAndBirthDate("11111111111", LocalDate.now())
        ).thenReturn(client);
        when(applicationService.getCreditApplicationResult(client)).thenReturn(
                result
        );

        clientService.findApplicationByIdentityNumberAndDateOfBirth("11111111111", LocalDate.now());
        assertEquals(client.getFirstName(), result.getFirstName());
        assertEquals(client.getLastName(), result.getLastName());
    }

    @Test
    void shouldGetClientInformation(){
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .creditApplication(CreditApplication.builder().id(1L).build())
                .creditScore(100)
                .build();
        ClientInformation checkInfo = ClientInformation.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .creditApplicationId(1L)
                .creditScore(100)
                .build();

        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(client);
        ClientInformation info = clientService.getClientInformation("11111111111");
        assertEquals(info.getFirstName(), checkInfo.getFirstName());
        assertEquals(info.getLastName(), checkInfo.getLastName());
        assertEquals(info.getCreditApplicationId(), checkInfo.getCreditApplicationId());
        assertEquals(info.getCreditScore(), checkInfo.getCreditScore());
    }
    @Test
    void shouldGetClientInformationThrowsExceptionWhenClientDoesntExist() {
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(null);
        assertThrows(ClientNotFoundException.class,
                () -> clientService.getClientInformation("11111111111")
        );
    }

    @Test
    void shouldGetClientObject(){
        CreditApplication application = CreditApplication.builder()
                .id(1L)
                .collateral(1f)
                .creditLimit(1f)
                .isApproved(1)
                .build();
        Client client = Client.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("11111111111")
                .phoneNumber("05555555555")
                .dateOfBirth(LocalDate.now())
                .monthlyIncome(1f)
                .creditScore(1)
                .creditApplication(application)
                .build();
        NewClient newClient = NewClient.builder()
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("11111111111")
                .phoneNumber("05555555555")
                .dateOfBirth(LocalDate.now())
                .monthlyIncome(1f)
                .collateral(1f)
                .build();
        Client createdClient = clientService.getClientObject(newClient, 1, application);
        assertEquals(client.getPhoneNumber(), createdClient.getPhoneNumber());
        assertEquals(client.getDateOfBirth(), createdClient.getDateOfBirth());
        assertEquals(client.getCreditApplication().getId(), createdClient.getCreditApplication().getId());
    }
}