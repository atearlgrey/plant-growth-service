package com.idc.plantgrowth.infrastructure.repository;

import com.idc.plantgrowth.domain.model.entity.GameState;
import com.idc.plantgrowth.domain.repository.GameStateRepository;
import com.idc.plantgrowth.infrastructure.mapper.GameStateInfraMapper;
import com.idc.plantgrowth.infrastructure.repository.jpa.GameStateJpaEntity;
import com.idc.plantgrowth.infrastructure.repository.jpa.GameStateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameStateRepositoryImpl implements GameStateRepository {

    private final GameStateJpaRepository gameStateJpaRepository;
    private final GameStateInfraMapper mapper;

    @Override
    public Optional<GameState> findByUserIdAndGameId(UUID userId, UUID gameId) {
        return gameStateJpaRepository.findByUserIdAndGameId(userId, gameId).map(mapper::toDomain);
    }

    @Override
    public GameState save(GameState state) {
        var gameStateJpa = mapper.toJpa(state);
        var saveResult = gameStateJpaRepository.save(gameStateJpa);
        return mapper.toDomain(saveResult);
    }

    @Override
    public void deleteById(UUID id) {
        gameStateJpaRepository.deleteById(id);
    }
}
