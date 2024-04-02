package com.petometry.activity.service;

import com.petometry.activity.rest.model.ActivityDto;
import com.petometry.activity.rest.model.ActivityRequest;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ActivityService {
    ActivityDto getCurrentActivity(Jwt jwt, String userId);

    ActivityDto startNewActivity(String userId, ActivityRequest activityRequest);
}
