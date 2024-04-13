package com.petometry.activity.repository;

import com.petometry.activity.repository.model.Activity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByOwnerId(String ownerId);

    boolean existsByOwnerId(String ownerId);

    @Transactional
    void deleteByOwnerId(String ownerId);
}