package com.petometry.activity.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner_id", nullable = false, unique = true)
    private String ownerId;

    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private ZonedDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActivityType type;

    @Column(name = "reward", nullable = false)
    private Double reward;

}