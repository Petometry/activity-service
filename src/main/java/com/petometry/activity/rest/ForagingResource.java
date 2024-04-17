package com.petometry.activity.rest;

import com.frameboter.rest.AbstractResource;
import com.petometry.activity.rest.model.foraging.ForagingActivity;
import com.petometry.activity.rest.model.foraging.ForagingDto;
import com.petometry.activity.rest.model.foraging.ForagingReward;
import com.petometry.activity.service.ForagingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/activities/foraging")
public class ForagingResource extends AbstractResource {

    private final ForagingService foragingService;

    // @formatter:off
    @Operation(summary = "Starts a new foraging activity", description = "Starts a new foraging activity. There can only be 1 active activity at any time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity started successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already has an active activity ", content = @Content),
    })
    @PostMapping()
    public ForagingDto startForaging(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid ForagingActivity foragingActivity) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("startForaging started for userId={} foragingActivity={}", userId, foragingActivity);
        ForagingDto foraging = foragingService.createForaging(userId, foragingActivity);
        log.info("startForaging finished for userId={} foraging={}", getUserId(jwt), foraging);
        return foraging;
    }

    // @formatter:off
    @Operation(summary = "Get current foraging activity", description = "Gets the current users current  foraging activity. Returns null if it doesn't exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
    })
    @GetMapping()
    public ForagingDto getForaging(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("getForaging started for userId={}", userId);
        ForagingDto foraging = foragingService.getForaging(jwt, userId);
        log.info("getForaging finished for userId={} foraging={}", getUserId(jwt), foraging);
        return foraging;
    }

    // @formatter:off
    @Operation(summary = "Deletes the current foraging activity", description = "Deletes the current users foraging activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not foraging", content = @Content),
    })
    @DeleteMapping()
    public void deleteForaging(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("deleteForaging started for userId={}", userId);
        foragingService.deleteForaging(userId);
        log.info("deleteForaging finished for userId={}", getUserId(jwt));
    }

    // @formatter:off
    @Operation(summary = "Collects the current foraging activity if it is collectable", description = "Collects the current users current foraging activity if it is finished and collectable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reward collected successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not foraging", content = @Content),
    })
    @PutMapping("/collectable")
    public ForagingReward collectForagingReward(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("collectForagingReward started for userId={}", userId);
        ForagingReward reward = foragingService.collectForagingReward(jwt, userId);
        log.info("collectForagingReward finished for userId={}", getUserId(jwt));
        return reward;
    }
}
