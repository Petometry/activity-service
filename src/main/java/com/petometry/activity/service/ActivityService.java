package com.petometry.activity.service;

import com.petometry.activity.rest.model.ActivityDto;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ActivityService {
    
    ActivityDto getCurrentActivity(Jwt jwt, String userId);

    void stopActivity(String userId);
    
    Boolean hasActivity(String userId);
}
