package com.patika.creditapplication.service.strategy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StrategyContext {
    private final List<CreditStrategy> creditStrategyList;


}
