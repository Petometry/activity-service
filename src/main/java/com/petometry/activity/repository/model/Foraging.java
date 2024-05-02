package com.petometry.activity.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "Foraging")
public class Foraging {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner_id", nullable = false, unique = true)
    private String ownerId;

    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private ZonedDateTime  endTime;

    @Column(name = "circle_reward", nullable = false)
    private Long circleReward;

    @Column(name = "triangle_reward", nullable = false)
    private Long triangleReward;

    @Column(name = "rectangle_reward", nullable = false)
    private Long rectangleReward;
}
