package com.petometry.activity.service;

import com.petometry.activity.repository.WorkRepository;
import com.petometry.activity.repository.model.Work;
import com.petometry.activity.rest.model.ActivityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

import static com.petometry.activity.repository.model.ActivityType.WORK;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    
    private final WorkRepository workRepository;

    @Override
    public ActivityDto getCurrentActivity(Jwt jwt, String userId) {

        Optional<Work> workOptional = workRepository.findByOwnerId(userId);
        if (workOptional.isEmpty()) {
            return null;
        }
        Work work = workOptional.get();
        return convertWorkToActivityDto(work);
    }

    @Override
    public Boolean hasActivity(String userId){
       return workRepository.existsByOwnerId(userId);
    }

    private static ActivityDto convertWorkToActivityDto(Work work) {
        Boolean isCollectable = ZonedDateTime.now().isAfter(work.getEndTime());
        ActivityDto activityDto = new ActivityDto();
        activityDto.setStartTime(work.getStartTime());
        activityDto.setEndTime(work.getEndTime());
        activityDto.setType(WORK);
        activityDto.setCollectable(isCollectable);
        return activityDto;
    }
}
