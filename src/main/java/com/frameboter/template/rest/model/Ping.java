package com.frameboter.template.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Ping {

    @Schema(name = "username", example = "roboter5123", description = "Name of the currently logged in user")
    private String userName;

    @Schema(name = "createdAt", example = "1999-06-13T10:00:00.0000000", description = "Date and time at which the ping was created", pattern = "YYYY-MM-DDThh:mm:ss.ms")
    private LocalDateTime createdAt;
}
