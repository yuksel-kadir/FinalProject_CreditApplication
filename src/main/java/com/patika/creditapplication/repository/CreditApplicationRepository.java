package com.patika.creditapplication.repository;

import com.patika.creditapplication.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<Application, Long> {

}
