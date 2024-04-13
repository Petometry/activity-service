package com.petometry.activity.service;

import com.petometry.activity.repository.ForagingRepository;
import com.petometry.activity.repository.WorkRepository;
import com.petometry.activity.repository.model.Foraging;
import com.petometry.activity.repository.model.Work;
import com.petometry.activity.rest.model.ActivityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

import static com.petometry.activity.repository.model.ActivityType.FORAGING;
import static com.petometry.activity.repository.model.ActivityType.WORK;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final WorkRepository workRepository;

    private final ForagingRepository foragingRepository;

    @Override
    public ActivityDto getCurrentActivity(Jwt jwt, String userId) {

        Optional<Work> workOptional = workRepository.findByOwnerId(userId);
        if (workOptional.isPresent()) {
            Work work = workOptional.get();
            return convertWorkToActivityDto(work);
        }

        Optional<Foraging> foragingOptional = foragingRepository.findByOwnerId(userId);
        if (foragingOptional.isPresent()) {
            Foraging foraging = foragingOptional.get();
            return convertForagingToActivity(foraging);
        }

        return null;
    }

    private ActivityDto convertForagingToActivity(Foraging foraging) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setStartTime(foraging.getStartTime());
        activityDto.setEndTime(foraging.getEndTime());
        activityDto.setType(FORAGING);
        activityDto.setCollectable(ZonedDateTime.now().isAfter(foraging.getEndTime()));
        return activityDto;
    }

    @Override
    public Boolean hasActivity(String userId) {
        return workRepository.existsByOwnerId(userId) || foragingRepository.existsByOwnerId(userId);
    }

    private static ActivityDto convertWorkToActivityDto(Work work) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setStartTime(work.getStartTime());
        activityDto.setEndTime(work.getEndTime());
        activityDto.setType(WORK);
        activityDto.setCollectable(ZonedDateTime.now().isAfter(work.getEndTime()));
        return activityDto;
    }
}
