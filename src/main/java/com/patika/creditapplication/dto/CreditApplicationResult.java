package com.patika.creditapplication.dto;

import com.patika.creditapplication.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreditApplicationResult {
    private String firstName;
    private String lastName;
    private String identityNumber;
    private Float creditLimit;
    private Integer creditScore;
    private CreditStatus creditStatus;
}
