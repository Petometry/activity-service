package com.petometry.activity.service;

import com.petometry.activity.repository.ActivityRepository;
import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.ActivityDto;
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

    private final ActivityRepository activityRepository;

    private final WorkService workService;

    private final ModelMapper modelMapper;

    @Override
    public ActivityDto getCurrentActivity(Jwt jwt, String userId) {

        Optional<Activity> activityOptional = activityRepository.findByOwnerId(userId);

        if (activityOptional.isEmpty()) {
            return null;
        }
        Activity activity = activityOptional.get();
        if (LocalDateTime.now().isAfter(activity.getEndTime())) {
            finishActivity(jwt, activity);
        }
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
        activityDto.setCollectable = LocalDateTime.now().isAfter(activity.getEndTime())
        return activityDto;
    }

    @Override
    public void stopActivity(String userId) {
        activityRepository.deleteByOwnerId(userId);
    }

    private void finishActivity(Jwt jwt, Activity activity) {

        if (WORK.equals(activity.getType())) {
            workService.finishActivity(jwt, activity);
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        activityRepository.delete(activity);
    }
}
