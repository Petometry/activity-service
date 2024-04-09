package com.petometry.activity.rest;

import com.frameboter.rest.AbstractResource;
import com.petometry.activity.rest.model.ActivityDto;
import com.petometry.activity.rest.model.WorkDto;
import com.petometry.activity.rest.model.work.WorkActivity;
import com.petometry.activity.service.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/activities/work")
public class WorkResource extends AbstractResource {

    private final WorkService workService;

    // @formatter:off
    @Operation(summary = "Starts a new work activity", description = "Starts a new work activity. There can only be 1 active activity at any time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity started successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already has an active activity ", content = @Content),
    })
    @PostMapping()
    public ActivityDto startWork(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid WorkActivity workActivity) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("startWork started for userId={} activityRequest={}", userId, workActivity);
        WorkDto work = workService.createWork(userId, workActivity);
        log.info("startWork finished for userId={} work={}", getUserId(jwt), work);
        return work;
    }
}
