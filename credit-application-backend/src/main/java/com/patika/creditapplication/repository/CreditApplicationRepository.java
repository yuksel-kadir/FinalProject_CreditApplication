package com.patika.creditapplication.repository;

import com.patika.creditapplication.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication, Long> {

}
