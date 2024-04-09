package com.petometry.activity.rest.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.petometry.activity.repository.model.Work}
 */
@Data
@ToString
public class WorkDto implements Serializable {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double reward;

    private Boolean collectable;
}
