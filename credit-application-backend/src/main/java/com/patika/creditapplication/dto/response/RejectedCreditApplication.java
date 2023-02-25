package com.patika.creditapplication.dto.response;

import org.springframework.stereotype.Component;

import static com.patika.creditapplication.util.CreditResultUtil.REJECTED;

@Component
public class RejectedCreditApplication extends CreditStatusBase{
    public  RejectedCreditApplication(){
        super(0, REJECTED);
    }
}
