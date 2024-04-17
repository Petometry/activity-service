package com.petometry.activity.service;

import com.petometry.activity.repository.WorkRepository;
import com.petometry.activity.repository.model.Work;
import com.petometry.activity.rest.model.work.WorkActivity;
import com.petometry.activity.rest.model.work.WorkDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

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
        work.setStartTime(ZonedDateTime.now());
        work.setEndTime(ZonedDateTime.now().plusHours(workActivity.getDuration()));
        work.setReward(calculateReward(work));
        Work createdWork = workRepository.save(work);
        return convertToWorkDto(createdWork);
    }

    @Override
    public WorkDto getWork(Jwt jwt, String userId) {
        Optional<Work> workOptional = workRepository.findByOwnerId(userId);
        if (workOptional.isEmpty()){
            return null;
        }
        Work work = workOptional.get();
        return convertToWorkDto(work);
    }

    @Override
    public void deleteWork(String userId) {

        workRepository.deleteByOwnerId(userId);
    }

    public void finishActivity(Jwt jwt, Work work) {
        currencyService.getPayedByServer(jwt, work.getOwnerId(), work.getReward());
        workRepository.deleteByOwnerId(work.getOwnerId());
    }

    public void collectWorkReward(Jwt jwt, String userId){
        Optional<Work> workOptional = workRepository.findByOwnerId(userId);
        if (workOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.valueOf(404));
        }
        Work work = workOptional.get();
        if (ZonedDateTime.now().isAfter(work.getEndTime())){
            finishActivity(jwt, work);
        }else{
            throw new ResponseStatusException(HttpStatus.valueOf(425));
        }
    }

    private WorkDto convertToWorkDto(Work createdWork) {
        WorkDto workDto = modelMapper.map(createdWork, WorkDto.class);
        workDto.setCollectable(ZonedDateTime.now().isAfter(workDto.getEndTime()));
        workDto.setType(WORK);
        return workDto;
    }

    private static double calculateReward(Work work) {
        long hoursBetween = work.getStartTime().until(work.getEndTime(), ChronoUnit.HOURS);
        double reward = hoursBetween * 0.1;
        return  Math.round(reward * 100.00) / 100.00;
    }
}
