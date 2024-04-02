package com.petometry.activity.rest;

import com.frameboter.rest.AbstractResource;
import com.petometry.activity.rest.model.ActivityDto;
import com.petometry.activity.rest.model.ActivityRequest;
import com.petometry.activity.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/activities")
public class ActivityResource extends AbstractResource {

    private final ActivityService activityService;

    // @formatter:off
    @Operation(summary = "Returns current activity", description = "Returns the users current activity. Returns null if the user has none")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity returned successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
    })
    @GetMapping()
    public ActivityDto getCurrentActivity(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("getCurrentActivity started for userId=" + userId);
        ActivityDto activity = activityService.getCurrentActivity(jwt, userId);
        log.info("getCurrentActivity finished for userId={} activity={}", getUserId(jwt), activity);
        return activity;
    }

    // @formatter:off
    @Operation(summary = "Starts a new Activity", description = "Starts a new activity. There can only be 1 active activity at any time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity started successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already has an active activity ", content = @Content),
    })
    @GetMapping()
    public ActivityDto startNewActivity(@AuthenticationPrincipal Jwt jwt, ActivityRequest activityRequest) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("startNewActivity started for userId={} activity={}", userId, activityRequest);
        ActivityDto activity = activityService.startNewActivity(userId, activityRequest);
        log.info("startNewActivity finished for userId={} activity={}", getUserId(jwt), activity);
        return activity;
    }
}
