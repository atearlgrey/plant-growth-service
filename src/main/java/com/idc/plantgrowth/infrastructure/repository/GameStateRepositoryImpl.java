package com.idc.plantgrowth.infrastructure.repository;

import com.idc.plantgrowth.domain.model.entity.GameState;
import com.idc.plantgrowth.domain.repository.GameStateRepository;
import com.idc.plantgrowth.infrastructure.mapper.GameStateInfraMapper;
import com.idc.plantgrowth.infrastructure.repository.jpa.GameStateJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameStateRepositoryImpl implements GameStateRepository {

    private final GameStateJpaRepository gameStateJpaRepository;
    private final GameStateInfraMapper mapper;

    @Override
    public Page<GameState> findAll(Pageable pageable) {
        return gameStateJpaRepository.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Optional<GameState> findById(UUID id) {
        return gameStateJpaRepository.findById(id).map(mapper::toDomain);
    }

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
    public boolean existsById(UUID id) {
        return gameStateJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(UUID id) {
        gameStateJpaRepository.deleteById(id);
    }
}
