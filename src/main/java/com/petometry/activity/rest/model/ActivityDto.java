package com.petometry.activity.rest.model;

import com.petometry.activity.repository.model.ActivityType;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.petometry.activity.repository.model.Activity}
 */
@Data
@ToString
public class ActivityDto implements Serializable {

    private ActivityType type;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double reward;

    private Boolean collectable;
}
