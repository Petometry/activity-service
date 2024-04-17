package com.petometry.activity.rest.model.foraging;

import com.petometry.activity.rest.model.ActivityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO for {@link com.petometry.activity.repository.model.Work}
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class ForagingDto extends ActivityDto {

    private ForagingReward reward;

}
