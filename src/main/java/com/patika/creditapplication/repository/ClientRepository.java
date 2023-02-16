package com.patika.creditapplication.repository;

import com.patika.creditapplication.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByIdentityNumberAndDateOfBirth(String socialNumber, LocalDate dateOfBirth);
}
