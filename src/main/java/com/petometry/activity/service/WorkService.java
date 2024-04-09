package com.petometry.activity.service;

import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.WorkDto;
import com.petometry.activity.rest.model.work.WorkActivity;
import org.springframework.security.oauth2.jwt.Jwt;

public interface WorkService {

    WorkDto createWork(String userId, WorkActivity workActivity);

    WorkDto getWork(String userId);

    void deleteWork(String userId);
    
    void finishActivity(Jwt jwt, Activity activity);
}
