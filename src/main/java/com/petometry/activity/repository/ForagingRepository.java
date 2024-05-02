package com.petometry.activity.repository;

import com.petometry.activity.repository.model.Foraging;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForagingRepository extends JpaRepository<Foraging, Long> {

    Optional<Foraging> findByOwnerId(String ownerId);

    boolean existsByOwnerId(String ownerId);

    @Transactional
    void deleteByOwnerId(String ownerId);
}
