package com.patika.creditapplication.adapter;

import com.patika.creditapplication.enums.Carrier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@RequiredArgsConstructor
public class SMSSenderClient implements SMSSender{
    private final List<SMSSenderAdapter> adapters;

    @Override
    public void sendSMS(Carrier carrier, String message, String phoneNumber) {
        SMSSenderAdapter adapter = adapters.stream()
                .filter(smsSenderAdapter -> smsSenderAdapter.getCarrier() == carrier)
                .findFirst()
                .get();
        adapter.sendSMSToClient(message, phoneNumber);
    }
}
