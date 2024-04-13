package com.petometry.activity.service;

import com.petometry.activity.service.model.currency.CurrencyBalance;
import com.petometry.activity.service.model.currency.CurrencyGeocoinBalance;
import org.springframework.security.oauth2.jwt.Jwt;

public interface CurrencyService {

    CurrencyGeocoinBalance getBalances(Jwt jwt, String userid);

    CurrencyBalance payServer(Jwt jwt, String userId, Double value);

    CurrencyBalance getPayedByServer(Jwt jwt, String userId, double value);


}
