package com.petometry.activity.service;

import com.petometry.activity.service.model.currency.CurrencyGeocoinsBalance;
import com.petometry.activity.service.model.currency.CurrencyBalances;
import org.springframework.security.oauth2.jwt.Jwt;

public interface CurrencyService {

    CurrencyBalances getBalances(Jwt jwt, String userid);

    CurrencyGeocoinsBalance payServer(Jwt jwt, String userId, Double value);

    CurrencyGeocoinsBalance getPayedByServer(Jwt jwt, String userId, double value);


}
