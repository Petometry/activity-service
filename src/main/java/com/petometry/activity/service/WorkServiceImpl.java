package com.petometry.activity.service;

import com.petometry.activity.repository.ActivityRepository;
import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.ActivityRequest;
import com.petometry.activity.service.model.currency.CurrencyBalance;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.petometry.activity.repository.model.ActivityType.WORK;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final ActivityRepository activityRepository;

    private final CurrencyService currencyService;

    private final ModelMapper modelMapper;

    @Override
    public Activity createActivity(String userId, ActivityRequest activityRequest) {

        if (LocalDateTime.now().plusHours(10).isBefore(activityRequest.getEndTime())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }

        Activity activity = modelMapper.map(activityRequest, Activity.class);
        activity.setType(WORK);
        activity.setOwnerId(userId);
        activity.setStartTime(LocalDateTime.now());
        return activityRepository.save(activity);
    }

    @Override
    public CurrencyBalance finishActivity(Jwt jwt, Activity activity) {

        long hoursBetween = activity.getStartTime().until(activity.getEndTime(), ChronoUnit.HOURS);
        double reward = hoursBetween * 0.1;
        return currencyService.getPayedByServer(jwt, activity.getOwnerId(), reward);
    }
}
