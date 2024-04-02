package com.petometry.activity.rest.model;

import com.petometry.activity.repository.model.ActivityType;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.petometry.activity.repository.model.Activity}
 */
@Value
@Data
@ToString
public class ActivityDto implements Serializable {

    ActivityType type;

    LocalDate startTime;

    LocalDateTime endTime;

}