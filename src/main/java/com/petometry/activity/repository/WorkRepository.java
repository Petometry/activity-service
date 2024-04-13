package com.petometry.activity.repository;

import com.petometry.activity.repository.model.Work;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {

    Optional<Work> findByOwnerId(String ownerId);

    boolean existsByOwnerId(String ownerId);

    @Transactional
    void deleteByOwnerId(String ownerId);
}
