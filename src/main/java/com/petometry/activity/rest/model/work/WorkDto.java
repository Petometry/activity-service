package com.petometry.activity.rest.model.work;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * DTO for {@link com.petometry.activity.repository.model.Work}
 */
@Data
@ToString
public class WorkDto implements Serializable {

    private ZonedDateTime startTime;

    private ZonedDateTime  endTime;

    private Double reward;

    private Boolean collectable;
}
