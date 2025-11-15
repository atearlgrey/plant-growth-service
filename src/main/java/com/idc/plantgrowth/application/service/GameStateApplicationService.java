package com.idc.plantgrowth.application.service;

import com.idc.plantgrowth.application.command.CreateGameStateCommand;
import com.idc.plantgrowth.application.command.UpdateGameStateCommand;
import com.idc.plantgrowth.domain.exception.BusinessException;
import com.idc.plantgrowth.domain.model.common.ErrorCode;
import com.idc.plantgrowth.domain.model.entity.GameState;
import com.idc.plantgrowth.domain.repository.GameStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameStateApplicationService {
    private final GameStateRepository repository;

    public GameState get(UUID userId, UUID gameId) {
        return repository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Game state not found for this user/game"
                ));
    }

    public Page<GameState> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public GameState create(CreateGameStateCommand cmd) {
        GameState state = new GameState(
                UUID.randomUUID(),
                cmd.userId(),
                cmd.gameId(),
                cmd.sceneId(),
                cmd.stateJson()
        );
        return repository.save(state);
    }

    public GameState update(UpdateGameStateCommand cmd) {
        var existing = repository.findByUserIdAndGameId(cmd.userId(), cmd.gameId())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "Game state not found for this user/game"
                ));

        existing.updateState(cmd.newStateJson());
        return repository.save(existing);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "Game state not found"
            );
        }

        repository.deleteById(id);
    }

}
