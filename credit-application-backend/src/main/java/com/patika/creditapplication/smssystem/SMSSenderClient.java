package com.patika.creditapplication.smssystem;

import com.patika.creditapplication.enums.Carrier;
import com.patika.creditapplication.exception.CarriertNotFoundException;
import com.patika.creditapplication.smssystem.adapter.SMSSenderAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SMSSenderClient implements SMSSender {
    private final List<SMSSenderAdapter> adapters;

    @Override
    public void sendSMS(Carrier carrier, String message, String phoneNumber) {
        try {
            SMSSenderAdapter adapter = adapters.stream()
                    .filter(smsSenderAdapter -> smsSenderAdapter.getCarrier() == carrier)
                    .findFirst()
                    .orElseThrow(CarriertNotFoundException::new);
            adapter.sendSMSToClient(message, phoneNumber);
        }catch (CarriertNotFoundException ex){
            log.error("Carrier not found. Couldn't send sms to the client.");
        }
    }
}
