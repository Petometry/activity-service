package com.petometry.activity.service;

import com.petometry.activity.rest.model.work.WorkActivity;
import com.petometry.activity.rest.model.work.WorkDto;
import com.petometry.activity.rest.model.work.WorkReward;
import org.springframework.security.oauth2.jwt.Jwt;

public interface WorkService {

    WorkDto createWork(String userId, WorkActivity workActivity);

    WorkDto getWork(Jwt jwt, String userId);

    void deleteWork(String userId);

    WorkReward collectWorkReward(Jwt jwt, String userId);
}
