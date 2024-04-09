package com.petometry.activity.service;

import com.petometry.activity.repository.ActivityRepository;
import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.ActivityDto;
import com.petometry.activity.repository.WorkRepository;
import com.petometry.activity.repository.model.Work;
import com.petometry.activity.rest.model.WorkDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.petometry.activity.repository.model.ActivityType.WORK;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    
    private final WorkRepository workRepository;

    private final ModelMapper modelMapper;

    @Override
    public ActivityDto getCurrentActivity(Jwt jwt, String userId) {

        Optional<Work> workOptional = workRepository.findByOwnerId(userId);
        if (workOptional.isEmpty()) {
            return null;
        }
        
        Work activity = workOptional.get();
        Boolean isCollectable = LocalDateTime.now().isAfter(activity.getEndTime());
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
        activityDto.setCollectable(isCollectable);
        return activityDto;
    }
    
    @Override
    public Boolean hasActivity(String userId){
       return workRepository.existsByOwnerId(userId);
    }

    @Override
    public void stopActivity(String userId) {
        activityRepository.deleteByOwnerId(userId);
    }
}
