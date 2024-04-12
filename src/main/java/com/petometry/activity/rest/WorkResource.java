package com.petometry.activity.rest;

import com.frameboter.rest.AbstractResource;
import com.petometry.activity.rest.model.work.WorkActivity;
import com.petometry.activity.rest.model.work.WorkDto;
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
import org.springframework.web.bind.annotation.*;

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
    public WorkDto startWork(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid WorkActivity workActivity) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("startWork started for userId={} activityRequest={}", userId, workActivity);
        WorkDto work = workService.createWork(userId, workActivity);
        log.info("startWork finished for userId={} work={}", getUserId(jwt), work);
        return work;
    }

    // @formatter:off
    @Operation(summary = "Get current work activity", description = "Gets the current users current activity. Returns null if it doesn't exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
    })
    @GetMapping()
    public WorkDto getWork(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("getWork started for userId={}", userId);
        WorkDto work = workService.getWork(jwt, userId);
        log.info("getWork finished for userId={} work={}", getUserId(jwt), work);
        return work;
    }

    // @formatter:off
    @Operation(summary = "Deletes the current work activity", description = "Deletes the current users current activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "activity retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not working", content = @Content),
    })
    @DeleteMapping()
    public void deleteWork(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("getWork started for userId={}", userId);
        workService.deleteWork(userId);
        log.info("getWork finished for userId={}", getUserId(jwt));
    }

    // @formatter:off
    @Operation(summary = "Collects the current work activity if it is collectable", description = "Collects the current users current work activity if it is finished and collectable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reward collected successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content),
            @ApiResponse(responseCode = "404", description = "User is not working", content = @Content),
    })
    @PutMapping("/collectable)
    public void collectWorkReward(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        String userId = getUserId(jwt);
        log.info("collectWorkReward started for userId={}", userId);
        workService.collectWorkReward(userId);
        log.info("collectWorkReward finished for userId={}", getUserId(jwt));
    }
}
