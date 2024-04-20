package com.petometry.activity.service;

import com.petometry.activity.repository.ForagingRepository;
import com.petometry.activity.repository.model.Foraging;
import com.petometry.activity.rest.model.foraging.ForagingActivity;
import com.petometry.activity.rest.model.foraging.ForagingDto;
import com.petometry.activity.rest.model.foraging.ForagingReward;
import com.petometry.activity.service.model.currency.CurrencyPetFoodBalances;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;

import static com.petometry.activity.repository.model.ActivityType.FORAGING;


@Service
@RequiredArgsConstructor
public class ForagingServiceImpl implements ForagingService {

    private final ForagingRepository foragingRepository;

    private final ActivityService activityService;

    private final CurrencyService currencyService;

    private final ModelMapper modelMapper;

    private final Random random;

    @Override
    public ForagingDto createForaging(String userId, ForagingActivity foragingActivity) {

        if (activityService.hasActivity(userId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }
        Foraging foraging = new Foraging();
        foraging.setOwnerId(userId);
        foraging.setStartTime(ZonedDateTime.now());
        foraging.setEndTime(ZonedDateTime.now().plusMinutes(foragingActivity.getDuration()));
        ForagingReward foragingReward = calculateReward(foragingActivity);
        foraging.setCircleReward(foragingReward.getCircle());
        foraging.setTriangleReward(foragingReward.getTriangle());
        foraging.setRectangleReward(foragingReward.getRectangle());
        Foraging createdForaging = foragingRepository.save(foraging);
        return convertToForagingDto(createdForaging);
    }

    @Override
    public ForagingDto getForaging(Jwt jwt, String userId) {
        Optional<Foraging> workOptional = foragingRepository.findByOwnerId(userId);
        if (workOptional.isEmpty()) {
            return null;
        }
        Foraging foraging = workOptional.get();
        return convertToForagingDto(foraging);
    }

    @Override
    public void deleteForaging(String userId) {

        foragingRepository.deleteByOwnerId(userId);
    }

    @Override
    public ForagingReward collectForagingReward(Jwt jwt, String userId) {
        Optional<Foraging> foragingOptional = foragingRepository.findByOwnerId(userId);
        if (foragingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.valueOf(404));
        }
        Foraging foraging = foragingOptional.get();
        if (ZonedDateTime.now().isAfter(foraging.getEndTime())) {
            finishActivity(jwt, foraging);
        } else {
            throw new ResponseStatusException(HttpStatus.valueOf(425));
        }
        ForagingReward reward = new ForagingReward();
        reward.setRectangle(foraging.getRectangleReward());
        reward.setTriangle(foraging.getTriangleReward());
        reward.setCircle(foraging.getCircleReward());
        return reward;
    }

    private void finishActivity(Jwt jwt, Foraging foraging) {

        CurrencyPetFoodBalances currencyPetFoodBalances = currencyService.getPetfoodBalances(jwt);
        currencyPetFoodBalances.setCircle(currencyPetFoodBalances.getCircle() + foraging.getCircleReward());
        currencyPetFoodBalances.setTriangle(currencyPetFoodBalances.getTriangle() + foraging.getTriangleReward());
        currencyPetFoodBalances.setRectangle(currencyPetFoodBalances.getRectangle() + foraging.getRectangleReward());
        currencyService.updatePetFoodBalances(jwt, currencyPetFoodBalances);
        foragingRepository.deleteByOwnerId(foraging.getOwnerId());
    }

    private ForagingDto convertToForagingDto(Foraging foraging) {

        ForagingDto foragingDto = modelMapper.map(foraging, ForagingDto.class);
        foragingDto.setCollectable(ZonedDateTime.now().isAfter(foragingDto.getEndTime()));
        foragingDto.setType(FORAGING);
        ForagingReward reward = new ForagingReward();
        reward.setCircle(foraging.getCircleReward());
        reward.setTriangle(foraging.getTriangleReward());
        reward.setRectangle(foraging.getRectangleReward());
        foragingDto.setReward(reward);
        return foragingDto;
    }

    private ForagingReward calculateReward(ForagingActivity foraging) {
        ForagingReward foragingReward = new ForagingReward();
        long maxReward = foraging.getDuration() * 5;
        foragingReward.setCircle(this.random.nextLong(foraging.getDuration(), maxReward - (2 * foraging.getDuration())));
        maxReward = maxReward - foragingReward.getCircle();
        foragingReward.setTriangle(this.random.nextLong(foraging.getDuration(), maxReward - foraging.getDuration()));
        maxReward = maxReward - foragingReward.getTriangle();
        foragingReward.setRectangle(maxReward);
        return foragingReward;
    }

}
