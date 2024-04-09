package com.petometry.activity.rest;

import com.frameboter.rest.AbstractResource;
import com.petometry.activity.rest.model.ActivityDto;
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
    @Operation(summary = "Returns current activity", description = "Returns the users current activity. Returns 404 if the user has none")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity returned successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not on an activity", content = @Content),
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
}
