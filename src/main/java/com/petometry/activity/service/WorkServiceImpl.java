package com.petometry.activity.service;

import com.petometry.activity.repository.ActivityRepository;
import com.petometry.activity.repository.model.Activity;
import com.petometry.activity.rest.model.ActivityDto;
import com.petometry.activity.repository.WorkRepository;
import com.petometry.activity.rest.model.work.WorkActivity;
import com.petometry.activity.repository.model.Work;
import com.petometry.activity.rest.model.WorkDto;
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

    private final WorkRepository workRepository;
    
    private final CurrencyService currencyService;

    private final ActivityService activityService;

    private final ModelMapper modelMapper;

        @Override
    public WorkDto createWork(String userId, WorkActivity workActivity) {
        if (activityService.hasActivity(userId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }

        Work work = new Work();
        work.setOwnerId(userId);
        work.setStartTime(LocalDateTime.now());
        work.setEndTime(LocalDateTime.now().plusHours(workActivity.getDuration()));
        work.setReward(calculateReward(work));
        Work createdWork = workRepository.save(work);
        return modelMapper.map(createdWork, WorkDto.class);
    }

    @Override
    public WorkDto getWork(String userId) {
        Optional<Work> workOptional = workRepository.findByOwnerId(userId);
        if(workOptional.isEmpty()){
            return null;
        }
        return modelMapper.map(workOptional.get(), WorkDto.class);
    }

    @Override
    public void finishActivity(Jwt jwt, Activity activity) {
        currencyService.getPayedByServer(jwt, activity.getOwnerId(), activity.getReward());
    }

    private static double calculateReward(Activity activity) {
        long hoursBetween = activity.getStartTime().until(activity.getEndTime(), ChronoUnit.HOURS);
        return hoursBetween * 0.1;
    }

        private static double calculateReward(Work work) {
        long hoursBetween = work.getStartTime().until(work.getEndTime(), ChronoUnit.HOURS);
        return hoursBetween * 0.1;
    }
}
