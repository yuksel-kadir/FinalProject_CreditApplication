package com.patika.creditapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditApplicationResult {
    private String firstName;
    private String lastName;
    private String identityNumber;
    private Float creditLimit;
    private Integer creditScore;
    private String creditStatus;
}
