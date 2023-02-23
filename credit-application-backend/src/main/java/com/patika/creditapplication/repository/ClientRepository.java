package com.patika.creditapplication.repository;

import com.patika.creditapplication.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByPhoneNumber(String phoneNumber);
    Client findClientByIdentityNumber(String identityNumber);
    Client findClientByIdentityNumberAndDateOfBirth(String identityNumber, LocalDate dateOfBirth);
    Client findClientByIdentityNumberAndPhoneNumber(String identityNumber, String phoneNumber);
    Client findClientByIdentityNumberOrPhoneNumber(String identity, String phoneNumber);
}
