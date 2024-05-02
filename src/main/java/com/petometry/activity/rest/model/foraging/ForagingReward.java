package com.petometry.activity.rest.model.foraging;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ForagingReward implements Serializable {

    private Long circle;
    private Long triangle;
    private Long rectangle;

}
