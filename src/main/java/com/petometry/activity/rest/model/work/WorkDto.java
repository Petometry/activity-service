package com.petometry.activity.rest.model.work;

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
public class WorkDto extends ActivityDto {

    private Double reward;

}
