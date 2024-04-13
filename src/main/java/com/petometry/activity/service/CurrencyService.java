package com.petometry.activity.service;

import com.petometry.activity.service.model.currency.CurrencyBalance;
import com.petometry.activity.service.model.currency.CurrencyPetFoodBalances;
import org.springframework.security.oauth2.jwt.Jwt;

public interface CurrencyService {

    CurrencyBalance getPayedByServer(Jwt jwt, String userId, double value);

    CurrencyPetFoodBalances getPetfoodBalances(Jwt jwt);

    CurrencyPetFoodBalances updatePetFoodBalances(Jwt jwt, CurrencyPetFoodBalances currencyPetFoodBalances);
}
