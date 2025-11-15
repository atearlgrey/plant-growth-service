package com.idc.plantgrowth.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameStateJpaRepository extends JpaRepository<GameStateJpaEntity, UUID> {
    Optional<GameStateJpaEntity> findByUserIdAndGameId(UUID userId, UUID gameId);

    boolean existsById(UUID id);
}
