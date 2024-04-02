package com.petometry.activity.rest.model;

import com.petometry.activity.repository.model.ActivityType;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ActivityRequest {

    private ActivityType type;

    private LocalDateTime endTime;
}
