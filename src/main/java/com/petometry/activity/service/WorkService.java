package com.petometry.activity.service;

import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.ActivityDto;
import com.petometry.activity.rest.model.work.WorkActivity;
import com.petometry.activity.rest.model.WorkDto;
import org.springframework.security.oauth2.jwt.Jwt;

public interface WorkService {

    WorkDto createWork(String userId, WorkActivity workActivity);
    
    void finishActivity(Jwt jwt, Activity activity);
}
