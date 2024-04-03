package com.petometry.activity.service;

import com.petometry.activity.repository.ActivityRepository;
import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.ActivityDto;
import com.petometry.activity.service.model.currency.CurrencyBalance;
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

        if (activityOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        Activity activity = activityOptional.get();
        if (LocalDateTime.now().isAfter(activity.getEndTime())){
            finishActivity(jwt, activity);
        }
        return modelMapper.map(activity, ActivityDto.class);
    }

    @Override
    public ActivityDto startNewActivity(String userId, ActivityDto activityRequest) {

        if (activityRequest.getEndTime() == null || LocalDateTime.now().isAfter(activityRequest.getEndTime())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }

        if (activityRepository.existsByOwnerId(userId)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }

        Activity activity;
        if (WORK.equals(activityRequest.getType())){
            activity = workService.createActivity(userId, activityRequest);
        }else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }

        return modelMapper.map(activity, ActivityDto.class);
    }

    @Override
    public void stopActivity(String userId) {
        activityRepository.deleteByOwnerId(userId);
    }

    private CurrencyBalance finishActivity(Jwt jwt, Activity activity) {

        CurrencyBalance currencyBalance;
        if (WORK.equals(activity.getType())){
            currencyBalance = workService.finishActivity(jwt, activity);
        }else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        activityRepository.delete(activity);
        return currencyBalance;
    }
}
