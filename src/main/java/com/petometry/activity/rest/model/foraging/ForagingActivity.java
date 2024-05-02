package com.petometry.activity.rest.model.foraging;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ForagingActivity {

    /**
     * Time in hours that the activity should go for
     */
    @NotNull
    @Min(value = 1, message = "Duration can not be shorter than 1 minute")
    @Max(value = 5, message = "Duration can not be longer than 5 minutes")
    private Long duration;
}
