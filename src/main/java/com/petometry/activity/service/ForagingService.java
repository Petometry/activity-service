package com.petometry.activity.service;

import com.petometry.activity.rest.model.foraging.ForagingActivity;
import com.petometry.activity.rest.model.foraging.ForagingDto;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ForagingService {
    ForagingDto createForaging(String userId, ForagingActivity foragingActivity);

    ForagingDto getForaging(Jwt jwt, String userId);

    void deleteForaging(String userId);

    void collectForagingReward(Jwt jwt, String userId);
}
