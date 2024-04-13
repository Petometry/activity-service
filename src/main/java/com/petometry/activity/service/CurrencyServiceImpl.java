package com.petometry.activity.service;

import com.frameboter.service.HttpService;
import com.petometry.activity.service.model.currency.CurrencyGeocoinsBalance;
import com.petometry.activity.service.model.currency.CurrencyBalances;
import com.petometry.activity.service.model.currency.CurrencyTransaction;
import com.petometry.activity.service.model.currency.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final HttpService httpService;

    @Value("${backend.domain.base}")
    private String baseDomain;

    @Value("${backend.domain.prefix}")
    private String urlPrefix;

    private static final String SERVICE_NAME = "currency.";

    @Override
    public CurrencyBalances getBalances(Jwt jwt, String userid) {

        String url = urlPrefix + SERVICE_NAME + baseDomain + "/geocoins";
        return httpService.sendGetRequest(url, jwt, CurrencyBalances.class);
    }

    @Override
    public CurrencyGeocoinsBalance payServer(Jwt jwt, String userId, Double value) {

        CurrencyTransaction currencyTransaction = new CurrencyTransaction();
        currencyTransaction.setCurrency(CurrencyType.GEOCOIN);
        currencyTransaction.setTarget("SERVER");
        currencyTransaction.setValue(value);
        currencyTransaction.setSource(userId);

        String url = urlPrefix + SERVICE_NAME + baseDomain + "/transactions";
        return httpService.sendPostRequest(url, jwt, currencyTransaction, CurrencyGeocoinsBalance.class);
    }

    @Override
    public CurrencyGeocoinsBalance getPayedByServer(Jwt jwt, String userId, double value) {

        CurrencyTransaction currencyTransaction = new CurrencyTransaction();
        currencyTransaction.setCurrency(CurrencyType.GEOCOIN);
        currencyTransaction.setTarget(userId);
        currencyTransaction.setValue(value);
        currencyTransaction.setSource("SERVER");

        String url = urlPrefix + SERVICE_NAME + baseDomain + "/transactions";
        return httpService.sendPostRequest(url, jwt, currencyTransaction, CurrencyGeocoinsBalance.class);
    }


}
