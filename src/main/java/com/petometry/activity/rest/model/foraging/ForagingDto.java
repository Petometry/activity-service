package com.petometry.activity.rest.model.foraging;

import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * DTO for {@link com.petometry.activity.repository.model.Work}
 */
@Data
@ToString
public class ForagingDto{

    private ZonedDateTime startTime;

    private ZonedDateTime  endTime;

    private ForagingReward reward;

    private Boolean collectable;
}
