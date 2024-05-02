package com.petometry.activity.rest.model;

import com.petometry.activity.repository.model.ActivityType;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * DTO for {@link com.petometry.activity.repository.model.Activity}
 */
@Data
@ToString
public class ActivityDto {

    private ActivityType type;

    private ZonedDateTime  startTime;

    private ZonedDateTime endTime;

    private Boolean collectable;
}
