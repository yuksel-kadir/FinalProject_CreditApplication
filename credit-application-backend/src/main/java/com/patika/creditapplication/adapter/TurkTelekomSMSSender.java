package com.patika.creditapplication.adapter;

import com.patika.creditapplication.enums.Carrier;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class TurkTelekomSMSSender implements SMSSenderAdapter{
    private final Carrier carrier = Carrier.TURKTELEKOM;

    @Override
    public void sendSMSToClient(String message, String phoneNumber) {
        //Turk Telekom related code here...
        log.info("Sending '{}' to '{}' using Turk Telekom SMS Sender.", message, phoneNumber);
    }
}
