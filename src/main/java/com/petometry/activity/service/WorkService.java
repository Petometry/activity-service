package com.petometry.activity.service;

import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.ActivityRequest;
import com.petometry.activity.service.model.currency.CurrencyBalance;
import org.springframework.security.oauth2.jwt.Jwt;

public interface WorkService {
    Activity createActivity(String userId, ActivityRequest activityRequest);

    CurrencyBalance finishActivity(Jwt jwt, Activity activity);
}
