package com.patika.creditapplication.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NaturalEntity extends Entity {
    private String firstName;
    private String lastName;
    private String socialNumber;
    private LocalDate dateOfBirth;
    private Integer creditScore;
    private Float monthlyIncome;
    private Float creditLimit;
    private Float collateral;
}
