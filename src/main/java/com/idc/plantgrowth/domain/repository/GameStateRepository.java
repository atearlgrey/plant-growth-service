package com.idc.plantgrowth.domain.repository;

import com.idc.plantgrowth.domain.model.entity.GameState;

import java.util.Optional;
import java.util.UUID;

public interface GameStateRepository {
    Optional<GameState> findByUserIdAndGameId(UUID userId, UUID gameId);

    GameState save(GameState state);

    void deleteById(UUID id);
}
