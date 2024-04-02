package com.frameboter.template.rest;

import com.frameboter.rest.AbstractResource;
import com.frameboter.template.rest.model.Ping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PingResource extends AbstractResource {

    // @formatter:off
    @Operation(summary = "Creates a ping ", description = "Creates a ping with the current time and the name of the currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ping created successfully"),
            @ApiResponse(responseCode = "401", description = "User is not logged in via Keycloak", content = @Content)
    })
    @PostMapping("/ping")
    Ping createPing(@AuthenticationPrincipal Jwt jwt) {
        // @formatter:on
        return new Ping(getUserName(jwt), LocalDateTime.now());
    }
}
