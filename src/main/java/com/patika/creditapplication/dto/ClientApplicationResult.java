package com.patika.creditapplication.dto;

import com.patika.creditapplication.enums.CreditStatus;
import lombok.Data;

@Data
public class ClientApplicationResult {
    private String firstName;
    private String lastName;
    private String identityNumber;
    private Float creditLimit;
    private CreditStatus creditStatus;
}
