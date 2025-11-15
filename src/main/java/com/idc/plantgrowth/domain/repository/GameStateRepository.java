package com.idc.plantgrowth.domain.repository;

import com.idc.plantgrowth.domain.model.entity.GameState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface GameStateRepository {
    Page<GameState> findAll(Pageable pageable);

    Optional<GameState> findById(UUID id);

    Optional<GameState> findByUserIdAndGameId(UUID userId, UUID gameId);

    GameState save(GameState state);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
