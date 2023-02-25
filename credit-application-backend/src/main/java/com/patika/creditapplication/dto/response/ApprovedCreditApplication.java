package com.patika.creditapplication.dto.response;

import org.springframework.stereotype.Component;

import static com.patika.creditapplication.util.CreditResultUtil.APPROVED;

@Component
public class ApprovedCreditApplication extends CreditStatusBase{
    public ApprovedCreditApplication(){
        super(1, APPROVED);
    }
}
