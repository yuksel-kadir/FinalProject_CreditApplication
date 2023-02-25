package com.patika.creditapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformation {
    private Integer creditScore;
    private Long creditApplicationId;
    private String firstName;
    private String lastName;
    private Float newCollateral;
    private Float newMonthlyIncome;
}
