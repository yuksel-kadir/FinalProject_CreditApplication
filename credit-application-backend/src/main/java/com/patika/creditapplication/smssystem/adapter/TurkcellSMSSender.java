package com.patika.creditapplication.smssystem.adapter;

import com.patika.creditapplication.enums.Carrier;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class TurkcellSMSSender implements SMSSenderAdapter{
    private final Carrier carrier = Carrier.TURKCELL;

    @Override
    public void sendSMSToClient(String message, String phoneNumber) {
        //Turkcell related code here...
        log.info("Sending '{}' to '{}' using Turkcell SMS Sender.", message, phoneNumber);
    }
}
