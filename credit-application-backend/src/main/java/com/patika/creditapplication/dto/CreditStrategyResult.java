package com.patika.creditapplication.dto;

import com.patika.creditapplication.dto.response.CreditStatusBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditStrategyResult {
    private CreditStatusBase creditStatus;
    private Float creditLimit;
    private Float collateral;
    private Integer creditScore;
}
