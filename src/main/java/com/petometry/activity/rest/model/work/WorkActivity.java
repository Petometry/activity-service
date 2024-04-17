package com.petometry.activity.rest.model.work;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WorkActivity {

    /**
     * Time in hours that the activity should go for
     */
    @NotNull
    @Min(value = 1, message = "Duration can not be shorter than 1 hours")
    @Max(value = 10, message = "Duration can not be longer than 10 hours")
    private Long duration;
}
