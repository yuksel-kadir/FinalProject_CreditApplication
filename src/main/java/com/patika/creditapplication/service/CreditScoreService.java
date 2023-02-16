package com.patika.creditapplication.service;

import com.patika.creditapplication.util.CreditScoreUtil;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreService {

    public Integer getCreditScore(){
          return CreditScoreUtil.generateRandomCreditScore();
    }
}
