package com.patika.creditapplication.service;

import com.patika.creditapplication.entity.Application;
import com.patika.creditapplication.entity.Client;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldAddClientWhenClientDoesntExist(){
        Client client1 = Client.builder()
                .id(1L)
                .firstName("Kadir")
                .lastName("Yüksel")
                .identityNumber("43945465268")
                .phoneNumber("05079788883")
                .dateOfBirth(LocalDate.of(1997, 10, 31))
                .creditScore(550)
                .monthlyIncome(3050f)
                .application(Application.builder().id(1L).build())
                .build();
        /*
        when(clientService.findClientByIdentityOrPhoneNumber(
                client1.getIdentityNumber(), client1.getPhoneNumber()))
                .thenReturn(null);
        */
        clientService.addClient(client1);
        //verify(clientRepository).save(client1);

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
    void shouldDeleteClientWhenExists(){
        Application application = Application.builder().id(1L).build();
        Client client = Client.builder().id(1L).application(application).build();
        when(clientRepository.findClientByIdentityNumber("11111111111")).thenReturn(client);
        clientService.deleteClient("11111111111");
        verify(clientRepository).delete(client);
    }

}